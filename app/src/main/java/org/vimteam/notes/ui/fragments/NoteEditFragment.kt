package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipDrawable
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_note_edit.*
import kotlinx.android.synthetic.main.fragment_note_view.*
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.base.formatTimestamp
import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.contracts.NoteContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.models.NavigationActions.*
import org.vimteam.notes.domain.viewmodels.NoteViewModel
import java.util.*


class NoteEditFragment : Fragment() {

    companion object {
        private const val NOTE_KEY = "note_key"

        fun newInstance(noteUid: String): Fragment {
            val noteEditFragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putString(NOTE_KEY, noteUid)
            noteEditFragment.arguments = bundle
            return noteEditFragment
        }
    }

    private val navigationViewModel by sharedViewModel<NavigationContract.ViewModel>()
    private val noteViewModel by viewModel<NoteContract.ViewModel>()

    private var noteUid: String = ""
    private var spannedLength: Int = 0

    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) setHasOptionsMenu(true)
        val arguments = arguments
        if (arguments == null || !arguments.containsKey(NOTE_KEY)) {
            return
        } else {
            noteUid = arguments.getString(NOTE_KEY, "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!navigationViewModel.twoPane) menu.clear()
        menu.removeItem(R.id.editNoteMenuItem)
        menu.removeItem(R.id.deleteNoteMenuItem)
        if (menu.findItem(R.id.saveNoteMenuItem) == null) inflater.inflate(R.menu.note_edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveNoteMenuItem -> {
                if (noteUid.isEmpty()) saveNote()
                else navigationViewModel.performAction(QUERY_UPDATE, noteUid)
            }
            R.id.cancelMenuItem -> activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)?.commitAllowingStateLoss()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        initDateTimePicker(getString(R.string.date))
        initTagsInputText()
        dateEditText.setOnFocusChangeListener { _, b ->
            if (b) datePickerDialog.show(childFragmentManager, "DatePickerDialog")
        }
        setObservers()
        if (noteUid == "") return
        noteViewModel.showNote(noteUid)
    }

    private fun setObservers() {
        noteViewModel.note.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            titleEditText.setText(it.title)
            dateEditText.setText(it.timestamp.formatTimestamp())
            dateEditText.tag = it.timestamp
            tagsEditText.setText(it.tags.toSimpleString())
            noteTextEditText.setText(it.noteText)
            markToggleButtonGroup.check(
                when (it.mark) {
                    Mark.IMPORTANT -> R.id.importantMarkButton
                    Mark.VALUES -> R.id.valueMarkButton
                    Mark.CONTACTS -> R.id.contactMarkButton
                    Mark.TODO -> R.id.todoMarkButton
                    Mark.NONE -> 0
                }
            )
        }
        noteViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        }
        navigationViewModel.navigationAction.observe(viewLifecycleOwner) {
            if (it == UPDATED) {
                if (navigationViewModel.twoPane && noteUid.isNotEmpty()) navigationViewModel.performAction(
                    READ,
                    noteUid
                )
                activity?.supportFragmentManager?.beginTransaction()?.remove(this)
                    ?.commitAllowingStateLoss()
            }
            if (it == DELETED) activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)?.commitAllowingStateLoss()
            if (it == UPDATE) saveNote()
        }
    }

    private fun initDateTimePicker(title: String, isThemeDark: Boolean = false) {
        datePickerDialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            val date = LocalDate(year, monthOfYear + 1, dayOfMonth)
            dateEditText.setText(date.toString())
            dateEditText.tag = date.toDateTimeAtStartOfDay().millis
            dateTextInputLayout.error = null
        }
        datePickerDialog.isThemeDark = isThemeDark
        datePickerDialog.showYearPickerFirst(false)
        datePickerDialog.setTitle(title)
    }

    private fun initTagsInputText() {
        tagsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null || s.isEmpty()) return
                if (s.toString().substring(s.length - 1) == ",") {
                    if (spannedLength >= s.length) return
                    val chiptext = s.toString().substring(spannedLength, s.length - 1)
                    if (chiptext.isEmpty()) return
                    val chip: ChipDrawable =
                        ChipDrawable.createFromResource(requireContext(), R.xml.chip)
                    chip.text = chiptext
                    if (chip.text.isNullOrEmpty()) return
                    chip.setBounds(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)
                    val span = ImageSpan(chip)
                    s.setSpan(span, spannedLength, s.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannedLength = s.length
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun saveNote() {
        val validationErrorsMap = noteViewModel.validateNote(
            timestamp = dateEditText.tag,
            title = titleEditText.text.toString()
        )
        setValidationErrors(validationErrorsMap)
        if (validationErrorsMap.isEmpty()) {
            noteViewModel.saveNote(
                uid = noteUid,
                timestamp = dateEditText.tag,
                tags = tagsEditText.text.toString(),
                mark = when (markToggleButtonGroup.checkedButtonId) {
                    R.id.importantMarkButton -> Mark.IMPORTANT
                    R.id.valueMarkButton -> Mark.VALUES
                    R.id.contactMarkButton -> Mark.CONTACTS
                    R.id.todoMarkButton -> Mark.TODO
                    else -> Mark.NONE
                },
                title = titleEditText.text.toString(),
                noteText = noteTextEditText.text.toString()
            ) {
                navigationViewModel.performAction(UPDATED, it)
            }
        }
    }

    private fun setValidationErrors(errorsMap: Map<String, String>) {
        dateTextInputLayout.error = ""
        titleTextInputLayout.error = ""
        for ((key, value) in errorsMap) {
            when (key) {
                NoteViewModel.DATE_FIELD -> dateTextInputLayout.error = value
                NoteViewModel.TITLE_FIELD -> titleTextInputLayout.error = value
            }
        }
    }
}
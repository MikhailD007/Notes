package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.chip.ChipDrawable
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_note_edit.*
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.base.formatTimestamp
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.contracts.NoteContract
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
            val noteUid = arguments.getString(NOTE_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.removeItem(R.id.editNoteMenuItem)
        menu.removeItem(R.id.deleteNoteMenuItem)
        if (menu.findItem(R.id.saveNoteMenuItem) == null) inflater.inflate(
            R.menu.note_edit_menu,
            menu
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveNoteMenuItem -> saveNote()
            R.id.cancelMenuItem -> activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)?.commitAllowingStateLoss()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        initDateTimePicker(getString(R.string.date))
        initTagsInputText()
        dateEditText.setOnFocusChangeListener { _, b ->
            if (b) datePickerDialog.show(
                childFragmentManager,
                "DatePickerDialog"
            )
        }
        if (noteUid == "") return
        noteViewModel.note.observe(viewLifecycleOwner) {
            titleEditText.setText(it.title)
            dateEditText.setText(it.timestamp.formatTimestamp())
            dateEditText.tag = it.timestamp
            noteEditText.setText(it.noteText)
        }
        noteViewModel.showNote(noteUid)
    }

    private fun initDateTimePicker(title: String, isThemeDark: Boolean = false) {
        datePickerDialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            dateEditText.setText(
                localeDateToString(
                    LocalDate(
                        year,
                        monthOfYear + 1,
                        dayOfMonth
                    )
                )
            )
            dateTextInputLayout.error = null
        }
        datePickerDialog.isThemeDark = isThemeDark
        datePickerDialog.showYearPickerFirst(false)
        datePickerDialog.setTitle(title)
    }

    private fun initTagsInputText() {
        tagsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null) return
                if (s.toString().substring(s.length - 1) == ",") {
                    val chip: ChipDrawable =
                        ChipDrawable.createFromResource(requireContext(), R.xml.chip)
                    chip.text = s.toString().substring(spannedLength, s.length - 1)
                    chip.setBounds(0, 0, chip.intrinsicWidth, chip.intrinsicHeight)
                    val span = ImageSpan(chip)
                    s.setSpan(
                        span,
                        spannedLength,
                        s.length - 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannedLength = s.length
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun localeDateToString(date: LocalDate, locale: Locale = Locale.getDefault()): String {
        return date.toString(DateTimeFormat.shortDate().withLocale(locale))
    }

    private fun saveNote() {
        //TODO save note using NotesViewModel
    }

}
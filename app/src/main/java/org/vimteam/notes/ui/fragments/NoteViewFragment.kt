package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_note_view.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.base.formatTimestamp
import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.contracts.NoteContract
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.viewmodels.NavigationViewModel

class NoteViewFragment : Fragment() {

    companion object {
        private const val NOTE_KEY = "note_key"

        fun newInstance(noteUid: String): Fragment {
            val noteViewFragment = NoteViewFragment()
            val bundle = Bundle()
            bundle.putString(NOTE_KEY, noteUid)
            noteViewFragment.arguments = bundle
            return noteViewFragment
        }
    }

    private val noteViewModel by viewModel<NoteContract.ViewModel>()
    private val navigationViewModel by sharedViewModel<NavigationContract.ViewModel>()

    private var noteUid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) setHasOptionsMenu(true)
        val arguments = arguments
        if (arguments == null || !arguments.containsKey(NOTE_KEY)) {
            return
        } else {
            noteUid = arguments.getString(NOTE_KEY, "")
        }
        if (noteUid == "") return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_note_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        noteViewModel.note.observe(viewLifecycleOwner) {
            view.titleTextView.text = it.title
            view.dateTextView.text = it.timestamp.formatTimestamp()
            view.tagsTextView.text = it.tags.toSimpleString()
            view.noteTextTextView.text = it.noteText
        }
        noteViewModel.showNote(noteUid)
        navigationViewModel.navigationAction.observe(viewLifecycleOwner) {
            if (it == NavigationActions.DELETE) activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)?.commitAllowingStateLoss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menu.findItem(R.id.editNoteMenuItem) == null) inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editNoteMenuItem -> navigationViewModel.showNote(NavigationActions.UPDATE, noteUid)
            R.id.deleteNoteMenuItem -> navigationViewModel.showNote(
                NavigationActions.DELETE,
                noteUid
            )
        }
        return super.onOptionsItemSelected(item)
    }

}
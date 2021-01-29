package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_note_view.view.*
import org.vimteam.notes.R
import org.vimteam.notes.base.formatTimestamp
import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.ui.interfaces.MenuItemSelectedHandler
import java.text.SimpleDateFormat
import java.util.*

class NoteViewFragment : Fragment() {

    companion object {
        private const val NOTE_KEY = "note_key"
        private const val TWO_PANE = "two_pane"

        fun newInstance(note: Note, twoPane: Boolean): Fragment {
            val noteViewFragment = NoteViewFragment()
            val bundle = Bundle()
            bundle.putParcelable(NOTE_KEY, note)
            bundle.putBoolean(TWO_PANE, twoPane)
            noteViewFragment.arguments = bundle
            return noteViewFragment
        }
    }

    private var note: Note? = null
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) setHasOptionsMenu(true)
        val arguments = arguments
        if (arguments == null || !arguments.containsKey(NOTE_KEY)) {
            return
        } else {
            note = arguments.getParcelable(NOTE_KEY)
            if (arguments.containsKey(TWO_PANE)) twoPane = arguments.getBoolean(TWO_PANE)
        }
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
        if (note == null) return
        view.titleTextView.text = note?.title
        view.dateTextView.text = note?.timestamp?.formatTimestamp()
        view.tagsTextView.text = note?.tags?.toSimpleString() ?: ""
        view.noteTextTextView.text = note?.noteText
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!twoPane) menu.clear()
        if (note == null) return
        if (menu.findItem(R.id.editNoteMenuItem) == null) inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editNoteMenuItem -> if (note != null) (activity as MenuItemSelectedHandler).editNote(note!!)
            R.id.deleteNoteMenuItem -> if (note != null) (activity as MenuItemSelectedHandler).deleteNote(note!!)
        }
        return super.onOptionsItemSelected(item)
    }

}
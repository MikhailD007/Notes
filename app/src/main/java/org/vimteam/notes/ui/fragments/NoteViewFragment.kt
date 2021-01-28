package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_note_view.view.*
import org.vimteam.notes.R
import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.domain.models.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteViewFragment: Fragment() {

    companion object {
        private const val NOTE_KEY="note_key"

        fun newInstance(note: Note) : Fragment{
            val noteViewFragment = NoteViewFragment()
            val bundle = Bundle()
            bundle.putParcelable(NOTE_KEY, note)
            noteViewFragment.arguments = bundle
            return noteViewFragment
        }
    }

    private var note: Note? = null

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
        val arguments = arguments
        if (arguments == null || !arguments.containsKey(NOTE_KEY)) {
            return
        } else {
            note = arguments.getParcelable(NOTE_KEY)
        }
        if (note == null) return
        view.titleTextView.text = note?.title
        view.dateTextView.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(note?.timestamp)
        view.tagsTextView.text = note?.tags?.toSimpleString() ?: ""
        view.noteTextTextView.text = note?.noteText


    }

}
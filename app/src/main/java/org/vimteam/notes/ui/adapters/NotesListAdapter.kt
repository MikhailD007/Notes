package org.vimteam.notes.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notes_list.view.*
import org.vimteam.notes.R
import org.vimteam.notes.base.inflate
import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.domain.models.Note
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(
    val notes: ArrayList<Note>
): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflatedView = parent.inflate(R.layout.item_notes_list, false)
        return NoteViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val itemNote = notes[position]
        holder.bindNote(itemNote)
    }

    override fun getItemCount() = notes.size

    //-------------------------------------------------------------------------------------------

    interface ClickEventHandler {
        fun notesListItemClick(v: View, note: Note?)
    }

    //-------------------------------------------------------------------------------------------

    class NoteViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var note: Note? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v!=null) (itemView.context as ClickEventHandler).notesListItemClick(v, note)
        }

        fun bindNote(note: Note) {
            this.note = note
            view.titleTextView.text = note.title
            view.dateTextView.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(note.timestamp)
            view.tagsTextView.text = note.tags.toSimpleString()
        }

        companion object {
            private const val NOTE_KEY = "NOTE"
        }
    }

}
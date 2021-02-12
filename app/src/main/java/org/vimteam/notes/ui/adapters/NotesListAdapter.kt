package org.vimteam.notes.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notes_list.view.*
import org.vimteam.notes.R
import org.vimteam.notes.base.inflate
import org.vimteam.notes.domain.models.Mark.*
import org.vimteam.notes.domain.models.NotesListElement
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(
    val notes: ArrayList<NotesListElement>,
    val fragment: EventHandler
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private var selectedNoteUid: String = ""

    fun getSelectedNoteUid() = selectedNoteUid

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

    inner class NoteViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var note: NotesListElement? = null

        init {
            v.setOnClickListener(this)
            registerContextMenu()
        }

        override fun onClick(v: View?) {
            if (v != null) fragment.notesListItemClick(v, note?.uid ?: "")
        }

        fun bindNote(note: NotesListElement) {
            this.note = note
            view.titleTextView.text = note.title
            view.dateTextView.text =
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(note.timestamp)
            view.tagsTextView.text = note.tags
            view.markImageView.setImageResource(note.mark.resourceId)
        }

        private fun registerContextMenu() {
            itemView.setOnLongClickListener {
                selectedNoteUid = note?.uid ?: ""
                return@setOnLongClickListener false
            }
            fragment.registerContextMenu(itemView)
        }
    }

    //-------------------------------------------------------------------------------------------

    interface EventHandler {

        fun notesListItemClick(v: View, noteUid: String)

        fun registerContextMenu(v: View)

    }

}
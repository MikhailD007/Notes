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
import java.util.*

class NotesListAdapter(
    val notes: ArrayList<Note>,
    val fragment: ContextMenuHandler
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    var selectedPos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflatedView = parent.inflate(R.layout.item_notes_list, false)
        return NoteViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val itemNote = notes[position]
        holder.bindNote(itemNote)
    }

    override fun getItemCount() = notes.size

    fun getSelectedPosition() = selectedPos

    //-------------------------------------------------------------------------------------------

    interface ClickEventHandler {
        fun notesListItemClick(v: View, note: Note?)
    }

    interface ContextMenuHandler {
        fun registerContextMenu(v: View)
    }

    //-------------------------------------------------------------------------------------------

    inner class NoteViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view: View = v
        private var note: Note? = null

        init {
            v.setOnClickListener(this)
            registerContextMenu()
        }

        override fun onClick(v: View?) {
            if (v != null) (itemView.context as ClickEventHandler).notesListItemClick(v, note)
        }

        fun bindNote(note: Note) {
            this.note = note
            view.titleTextView.text = note.title
            view.dateTextView.text =
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(note.timestamp)
            view.tagsTextView.text = note.tags.toSimpleString()
        }

        private fun registerContextMenu() {
            itemView.setOnLongClickListener {
                selectedPos = layoutPosition
                return@setOnLongClickListener false
            }
            fragment.registerContextMenu(itemView)
        }
    }

}
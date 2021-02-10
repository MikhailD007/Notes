package org.vimteam.notes.data.repositories

import org.vimteam.notes.data.`interface`.FirestoreDatabaseContract
import org.vimteam.notes.data.mappers.NoteMapper
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement
import kotlin.collections.ArrayList

class NotesRepository(private val db: FirestoreDatabaseContract) : NotesRepositoryContract {

    override fun getNotesList(func: (ArrayList<NotesListElement>) -> Unit) {
        db.getNoteDBList() {
            func.invoke(it.map(NoteMapper::toNotesListElement) as ArrayList<NotesListElement>)
        }
    }

    override fun getNote(noteUid: String, func: (Note?) -> Unit) {
        db.getNoteDB(noteUid) {
            func.invoke(NoteMapper.toNote(it))
        }
    }

    override fun setNote(note: Note, func: (Boolean)->Unit) {
        db.setNoteDB(NoteMapper.toNoteDB(note)) {
            func.invoke(true)
        }
    }

    override fun deleteNote(noteUid: String, func: (Boolean)->Unit) {
        db.deleteNoteDB(noteUid) {
            func.invoke(true)
        }
    }
}
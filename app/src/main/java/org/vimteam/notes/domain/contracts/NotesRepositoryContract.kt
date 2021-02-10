package org.vimteam.notes.domain.contracts

import org.vimteam.notes.data.models.NoteDBListElement
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement

interface NotesRepositoryContract {

    @Throws(Exception::class)
    fun getNotesList(func: (ArrayList<NotesListElement>) -> Unit)

    @Throws(Exception::class)
    fun getNote(noteUid: String, func: (Note?) -> Unit)

}
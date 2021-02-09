package org.vimteam.notes.domain.contracts

import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement

interface NotesRepositoryContract {

    @Throws(Exception::class)
    fun getNotesList(): ArrayList<NotesListElement>

    @Throws(Exception::class)
    fun getNote(noteUid: String): Note

}
package org.vimteam.notes.domain.contracts

import org.vimteam.notes.domain.models.Note

interface NotesListRepositoryContract {

    @Throws(Exception::class)
    fun getNotesList(): ArrayList<Note>

}
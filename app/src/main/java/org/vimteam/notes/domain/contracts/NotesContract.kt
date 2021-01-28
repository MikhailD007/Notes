package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note

interface NotesContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val notesList: LiveData<ArrayList<Note>>
        abstract val note: LiveData<Note>

        @Throws(Exception::class)
        abstract fun getNotesList()

        @Throws(Exception::class)
        abstract fun getNotesList(filterByMark: Mark)

        @Throws(Exception::class)
        abstract fun getNotesList(filterByTag: String)
    }



}
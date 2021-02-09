package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement

interface NotesListContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val notesList: LiveData<ArrayList<NotesListElement>>

        @Throws(Exception::class)
        abstract fun getNotesList()

        @Throws(Exception::class)
        abstract fun getNotesList(filterByMark: Mark)

        @Throws(Exception::class)
        abstract fun getNotesList(filterByTag: String)

        @Throws(Exception::class)
        abstract fun deleteNote(noteUid: String)

    }

}
package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.models.Note

interface NotesContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val notesList: LiveData<ArrayList<Note>>
        abstract val note: LiveData<Note>
        abstract val navigation: LiveData<NavigationActions>

        @Throws(Exception::class)
        abstract fun getNotesList()

        @Throws(Exception::class)
        abstract fun getNotesList(filterByMark: Mark)

        @Throws(Exception::class)
        abstract fun getNotesList(filterByTag: String)

        @Throws(Exception::class)
        abstract fun showAbout()

        @Throws(Exception::class)
        abstract fun createNote()

        @Throws(Exception::class)
        abstract fun editNote(selectedPos: Int)

        @Throws(Exception::class)
        abstract fun deleteNote(selectedPos: Int)

    }

}
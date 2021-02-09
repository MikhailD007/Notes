package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Note

interface NoteContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val note: LiveData<Note>

        @Throws(Exception::class)
        abstract fun showNote(noteUid: String)

        @Throws(Exception::class)
        abstract fun saveNote(noteUid: String)

    }

}
package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Note

interface NoteContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val note: LiveData<Note>
        abstract val error: LiveData<java.lang.Exception>

        abstract fun showNote(noteUid: String)

        abstract fun validateNote(note: Note, func: (Map<String,String>) -> Unit)
        abstract fun saveNote(note: Note, func: (String) -> Unit)

    }

}
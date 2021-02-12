package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note

interface NoteContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val note: LiveData<Note>
        abstract val error: LiveData<java.lang.Exception>

        abstract fun showNote(noteUid: String)

        abstract fun validateNote(timestamp: Any?, title: String): Map<String, String>
        abstract fun saveNote(uid: String, timestamp: Any, tags: String, mark: Mark, title: String, noteText: String, func: (String) -> Unit)

    }

}
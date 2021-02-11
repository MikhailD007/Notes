package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NoteContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Note

class NoteViewModel(
    private val repo: NotesRepositoryContract,
    private val res: ResourcesProviderContract
) : NoteContract.ViewModel() {

    override val note = MutableLiveData<Note>()
    override val error = MutableLiveData<Exception>()

    override fun showNote(noteUid: String) {
        try {
            repo.getNote(noteUid) {
                if (it == null) {
                    error.value = Exception(res.getString("no_note_found"))
                } else note.value = it
            }
        } catch (e: Exception) {
            error.value = e
        }
    }

    override fun validateNote(note: Note, func: (Map<String, String>) -> Unit) {
        val errorsMap = mutableMapOf<String, String>()
        if (note.title.isEmpty()) errorsMap["title"] = res.getString("no_title")
        if (note.timestamp == null) errorsMap["date"] = res.getString("no_date")
        func.invoke(errorsMap)
    }

    override fun saveNote(note: Note, func: (String) -> Unit) {
        try {
            repo.setNote(note) {
                func.invoke(note.uid)
            }
        } catch (e: Exception) {
            error.value = e
        }

    }

    init {
        note.value = null
    }

}
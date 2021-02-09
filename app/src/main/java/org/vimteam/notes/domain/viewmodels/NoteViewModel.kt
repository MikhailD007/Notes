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

    override fun showNote(noteUid: String) {
        note.value = repo.getNote(noteUid)
    }

    override fun saveNote(noteUid: String) {
        TODO("Not yet implemented")
    }

    init {
        note.value = null
    }

}
package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NotesContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note

class NotesViewModel(
    private val repo: NotesRepositoryContract,
    private val res: ResourcesProviderContract
): NotesContract.ViewModel() {

    override val notesList = MutableLiveData<ArrayList<Note>>()
    override val note = MutableLiveData<Note>()

    init {
        notesList.value = ArrayList()
    }

    override fun getNotesList() {
        notesList.value = repo.getNotesList()
    }

    override fun getNotesList(filterByMark: Mark) {
        TODO("Not yet implemented")
    }

    override fun getNotesList(filterByTag: String) {
        TODO("Not yet implemented")
    }

}
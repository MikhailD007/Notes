package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NotesListContract
import org.vimteam.notes.domain.contracts.NotesListRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note

class NotesListViewModel(
    private val repo: NotesListRepositoryContract,
    private val res: ResourcesProviderContract
): NotesListContract.ViewModel() {

    override val notesList = MutableLiveData<ArrayList<Note>>()

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
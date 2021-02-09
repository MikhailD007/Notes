package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NotesListContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.NotesListElement

class NotesListViewModel(
    private val repo: NotesRepositoryContract,
    private val res: ResourcesProviderContract
) : NotesListContract.ViewModel() {

    override val notesList = MutableLiveData<ArrayList<NotesListElement>>()

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

    override fun deleteNote(noteUid: String) {
        val note = notesList.value?.find {
            it.uid == noteUid
        }
        if (note != null) notesList.value?.remove(note)
    }

}
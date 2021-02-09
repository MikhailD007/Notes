package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NotesContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.models.NavigationActions.*
import org.vimteam.notes.domain.models.Note

class NotesViewModel(
    private val repo: NotesRepositoryContract,
    private val res: ResourcesProviderContract
): NotesContract.ViewModel() {

    override val notesList = MutableLiveData<ArrayList<Note>>()
    override val note = MutableLiveData<Note>()
    override val navigation = MutableLiveData<NavigationActions>()

    init {
        notesList.value = ArrayList()
        note.value = null
        navigation.value = NONE
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

    override fun showAbout() {
        navigation.value = ABOUT
    }

    override fun createNote() {
        navigation.value = CREATE
    }

    override fun editNote(selectedPos: Int) {
        note.value = notesList.value?.get(selectedPos)
        navigation.value = UPDATE
    }

    override fun deleteNote(selectedPos: Int) {
        notesList.value?.removeAt(selectedPos)
        navigation.value = DELETE
    }

}
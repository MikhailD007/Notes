package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.LiveData
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
    override val error = MutableLiveData<Exception>()

    init {
        notesList.value = ArrayList()
    }

    override fun getNotesList() {
        try {
            repo.getNotesList() {
                notesList.value = it
            }
        } catch (e: Exception) {
            error.value = e
        }
    }

    override fun getNotesList(filterByMark: Mark) {
        TODO("Not yet implemented")
    }

    override fun getNotesList(filterByTag: String) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(noteUid: String, func: () -> Unit) {
        try {
            repo.deleteNote(noteUid) {
                val note = notesList.value?.find {
                    it.uid == noteUid
                }
                if (note != null) {
                    notesList.value?.remove(note)
                    notesList.value = notesList.value
                }
                func.invoke()
            }
        } catch (e: Exception) {
            error.value = e
            return
        }
    }

}
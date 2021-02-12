package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.fragment_note_edit.*
import org.vimteam.notes.R
import org.vimteam.notes.domain.contracts.NoteContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note
import java.util.*

class NoteViewModel(
    private val repo: NotesRepositoryContract,
    private val res: ResourcesProviderContract
) : NoteContract.ViewModel() {

    companion object {
        const val DATE_FIELD = "date"
        const val TITLE_FIELD = "title"
    }

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

    override fun validateNote(timestamp: Any?, title: String): Map<String, String> {
        val errorsMap = mutableMapOf<String, String>()
        if (title.isEmpty()) errorsMap[TITLE_FIELD] = res.getString("no_title")
        if (timestamp == null) errorsMap[DATE_FIELD] = res.getString("no_date")
        return errorsMap
    }

    override fun saveNote(uid: String, timestamp: Any, tags: String, mark: Mark, title: String, noteText: String, func: (String) -> Unit) {
        var tagsString = tags
        if (tagsString.endsWith(",")) tagsString = tagsString.substring(0, tagsString.length - 1)
        val tagsArray = tagsString.split(",").toTypedArray()
        val note = Note(
            uid = if (uid == "") UUID.randomUUID().toString() else uid,
            timestamp = timestamp as Long,
            tags = tagsArray,
            mark = mark,
            title = title,
            noteText = noteText
        )
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
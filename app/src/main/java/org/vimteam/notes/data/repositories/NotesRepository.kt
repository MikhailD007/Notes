package org.vimteam.notes.data.repositories

import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement
import java.util.*
import kotlin.collections.ArrayList

class NotesRepository(): NotesRepositoryContract {

    override fun getNotesList(): ArrayList<NotesListElement> {
        val notesList: ArrayList<NotesListElement> = ArrayList()
        notesList.add(
            NotesListElement(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "Sample Title",
            tags = "tag1, some tag, home, todo, 123456, Elena, warning",
            mark = Mark.CONTACTS
        )
        )
        notesList.add(
            NotesListElement(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "New note",
            tags = "tag2, another tag, work, todo, 675, information",
            mark = Mark.NONE
        )
        )
        notesList.add(
            NotesListElement(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "One more note",
            tags = "tag3, other tag, hobby, todo, 675, information",
            mark = Mark.VALUES
        )
        )
        notesList.add(
            NotesListElement(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "note 4",
            tags = "tag4, another tag, home, todo, 675, information",
            mark = Mark.IMPORTANT
        )
        )
        return notesList
    }

    override fun getNote(noteUid: String): Note {
        return Note(
            uid = noteUid,
            timestamp = System.currentTimeMillis(),
            title = "Sample Title",
            noteText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            tags = arrayOf("tag1", "some tag", "home", "todo", "123456", "Elena", "warning"),
            mark = Mark.CONTACTS
        )
    }
}
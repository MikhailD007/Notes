package org.vimteam.notes.data.repositories

import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.domain.contracts.NotesListRepositoryContract
import org.vimteam.notes.domain.models.Mark
import org.vimteam.notes.domain.models.Note
import java.util.*
import kotlin.collections.ArrayList

class NotesListRepository(
    private val notesListDB: ArrayList<NoteDB>
): NotesListRepositoryContract {

    override fun getNotesList(): ArrayList<Note> {
        val notesList: ArrayList<Note> = ArrayList()
        notesList.add(Note(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "Sample Title",
            noteText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            tags = arrayOf("tag1", "some tag", "home", "todo", "123456", "Elena", "warning"),
            mark = Mark.CONTACTS
        ))
        notesList.add(Note(
            uid = UUID.randomUUID().toString(),
            timestamp = System.currentTimeMillis(),
            title = "Newt note",
            noteText = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.",
            tags = arrayOf("tag2", "another tag", "work", "todo", "675", "information"),
            mark = Mark.CONTACTS
        ))
        return notesList
    }
}
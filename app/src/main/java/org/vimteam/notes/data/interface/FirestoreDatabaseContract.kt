package org.vimteam.notes.data.`interface`

import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.data.models.NoteDBListElement

interface FirestoreDatabaseContract {

    @Throws(Exception::class)
    fun getNoteDBList(func: (ArrayList<NoteDBListElement>) -> Unit): ArrayList<NoteDBListElement>

    @Throws(Exception::class)
    fun getNoteDB(noteDBUid: String, func: (NoteDB?) -> Unit)

    @Throws(Exception::class)
    fun setNoteDB(noteDB: NoteDB, func: (Boolean)->Unit)

    @Throws(Exception::class)
    fun deleteNoteDB(noteDBUid: String, func: (Boolean)->Unit)
}
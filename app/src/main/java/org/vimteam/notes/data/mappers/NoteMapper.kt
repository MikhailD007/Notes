package org.vimteam.notes.data.mappers

import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.domain.models.Note
import java.sql.Date

object NoteMapper {

    fun toDB(note: Note): NoteDB {
        val noteDB = NoteDB()
        with (noteDB) {
            uid = note.uid
            timestamp = Date(note.timestamp)
            tags = note.tags.toSimpleString()
            mark = MarkMapper.toDB(note.mark)
            title = note.title
            noteText = note.noteText
        }
        return noteDB
    }

}
package org.vimteam.notes.data.mappers

import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.data.models.NoteDBListElement
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement
import java.sql.Date

object NoteMapper {

    fun toNotesListElement(noteDBListElement: NoteDBListElement): NotesListElement {
        return  NotesListElement(
            uid = noteDBListElement.uid,
            timestamp = noteDBListElement.timestamp,
            tags = noteDBListElement.tags,
            mark = MarkMapper.toMark(noteDBListElement.mark),
            title = noteDBListElement.title
        )
    }

    fun toNote(noteDB: NoteDB?): Note? {
        if (noteDB == null) return null
        return Note(
            uid = noteDB.uid,
            timestamp = noteDB.timestamp,
            tags = noteDB.tags.split(", ") as Array<String>,
            mark = MarkMapper.toMark(noteDB.mark),
            title = noteDB.title,
            noteText = noteDB.noteText
        )
    }

    fun toNoteDB(note: Note): NoteDB {
        return NoteDB(
            uid = note.uid,
            timestamp = note.timestamp,
            tags = note.tags.toSimpleString(),
            mark = MarkMapper.toDB(note.mark),
            title = note.title,
            noteText = note.noteText
        )
    }

}
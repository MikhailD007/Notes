package org.vimteam.notes.data.mappers

import org.vimteam.notes.base.toSimpleString
import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.data.models.NoteDBListElement
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.domain.models.NotesListElement
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

    fun toListElementModel(noteDBListElement: NoteDBListElement): NotesListElement {
        return  NotesListElement(
            uid = noteDBListElement.uid,
            timestamp = noteDBListElement.timestamp.m
        )
    }

    fun toNote(noteDB: NoteDB?): Note? {
        val note = Note(

        )
        return note
    }

}
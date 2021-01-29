package org.vimteam.notes.ui.interfaces

import org.vimteam.notes.domain.models.Note

interface MenuItemSelectedHandler {
    fun showAbout()
    fun addNewNote()
    fun editNote(note: Note)
    fun deleteNote(note: Note)
}
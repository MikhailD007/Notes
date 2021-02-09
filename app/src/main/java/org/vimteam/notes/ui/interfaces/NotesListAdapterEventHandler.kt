package org.vimteam.notes.ui.interfaces

import android.view.View

interface NotesListAdapterEventHandler {

    fun notesListItemClick(v: View, noteUid: String)

    fun registerContextMenu(v: View)

}
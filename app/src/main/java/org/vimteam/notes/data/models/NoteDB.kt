package org.vimteam.notes.data.models

import java.sql.Date

data class NoteDB(
    val uid: String,
    val timestamp: Date,
    val tags: String,
    val mark: Int,
    val title: String,
    val noteText: String
)

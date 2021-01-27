package org.vimteam.notes.domain.models

import java.sql.Date

data class Note(
    val uid: String,
    val timestamp: Date,
    val tags: Array<String>,
    val mark: Mark,
    val title: String,
    val noteText: String
)
package org.vimteam.notes.data.models

import java.sql.Date

data class Notes(
    val uid: String,
    val timestamp: Date,
    val tags: Array<String>,
    val mark: Int,
    val noteText: String
)

package org.vimteam.notes.domain.models

import java.sql.Date

data class Notes(
    val uid: String,
    val timestamp: Date,
    val tags: Array<String>,
    val mark: Marks,
    val noteText: String
)
package org.vimteam.notes.data.models

import org.vimteam.notes.base.formatTimestamp
import org.vimteam.notes.domain.models.Note

data class NoteDB (
    val uid: String,
    val timestamp: Long,
    val tags: String,
    val mark: Int,
    val title: String,
    val noteText: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteDB

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }

    override fun toString() = "${timestamp.formatTimestamp()} $title"
}

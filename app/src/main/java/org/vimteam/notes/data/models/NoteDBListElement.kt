package org.vimteam.notes.data.models

import org.vimteam.notes.base.formatTimestamp

data class NoteDBListElement(
    val uid: String,
    val timestamp: Long,
    val tags: String,
    val mark: Int,
    val title: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteDBListElement

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }

    override fun toString() = "${timestamp.formatTimestamp()} $title"
}

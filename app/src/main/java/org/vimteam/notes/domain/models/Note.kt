package org.vimteam.notes.domain.models

import java.time.Instant

data class Note(
    val uid: String,
    val timestamp: Long,
    val tags: Array<String>,
    val mark: Mark,
    val title: String,
    val noteText: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }
}
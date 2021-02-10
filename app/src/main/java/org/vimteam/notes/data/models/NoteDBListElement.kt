package org.vimteam.notes.data.models

import org.vimteam.notes.base.formatTimestamp

class NoteDBListElement {
    var uid: String = ""
    var timestamp: Long = 0
    var tags: String = ""
    var mark: Int = 0
    var title: String =""

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

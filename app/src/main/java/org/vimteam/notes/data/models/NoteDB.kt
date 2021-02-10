package org.vimteam.notes.data.models

import org.vimteam.notes.base.formatTimestamp

class NoteDB {
    var uid: String = ""
    var timestamp: Long = 0
    var tags: String = ""
    var mark: Int = 0
    var title: String = ""
    var noteText: String = ""

    constructor(
        uid: String,
        timestamp: Long,
        tags: String,
        mark: Int,
        title: String,
        noteText: String
    ) {
        this.uid = uid
        this.timestamp = timestamp
        this.tags = tags
        this.mark = mark
        this.title = title
        this.noteText = noteText
    }

    constructor()

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

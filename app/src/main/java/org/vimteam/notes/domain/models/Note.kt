package org.vimteam.notes.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.vimteam.notes.base.formatTimestamp

@Parcelize
data class Note(
    val uid: String,
    val timestamp: Long,
    val tags: Array<String>,
    val mark: Mark,
    val title: String,
    val noteText: String
) :Parcelable {
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

    override fun toString() = "${timestamp.formatTimestamp()} $title"
}
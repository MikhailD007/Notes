package org.vimteam.notes.data.models

import java.sql.Date

class NoteDB {
    var uid: String =""
    var timestamp: Date = Date(System.currentTimeMillis())
    var tags: String = ""
    var mark: Int = 0
    var title: String = ""
    var noteText: String = ""
}

package org.vimteam.notes.domain.models

import org.vimteam.notes.R

enum class Mark(val resourceId: Int) {
    NONE (0),
    IMPORTANT (R.drawable.ic_important_note),
    TODO (R.drawable.ic_todo_list),
    VALUES (R.drawable.ic_value),
    CONTACTS (R.drawable.ic_contact);
}
package org.vimteam.notes.data.mappers

import org.vimteam.notes.domain.models.Mark

object MarkMapper {

    fun toDB(mark: Mark) = mark.ordinal

}
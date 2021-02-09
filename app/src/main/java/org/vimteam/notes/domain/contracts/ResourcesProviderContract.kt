package org.vimteam.notes.domain.contracts

interface ResourcesProviderContract {

    fun getString(id: Int): String

    fun getString(stringName: String): String
}
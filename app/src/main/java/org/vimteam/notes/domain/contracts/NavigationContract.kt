package org.vimteam.notes.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.notes.domain.models.NavigationActions

interface NavigationContract {

    abstract class ViewModel : androidx.lifecycle.ViewModel() {

        abstract val navigationAction: LiveData<NavigationActions>

        @Throws(Exception::class)
        abstract fun showAbout()

        @Throws(Exception::class)
        abstract fun performAction(navigationAction: NavigationActions, noteUid: String)

        abstract fun getNoteUid(): String

    }
}
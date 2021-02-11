package org.vimteam.notes.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.models.NavigationActions

class NavigationViewModel() : NavigationContract.ViewModel() {

    override val navigationAction = MutableLiveData<NavigationActions>()
    private var noteUid: String = ""

    override fun showAbout() {
        navigationAction.value = NavigationActions.ABOUT
    }

    override fun performAction(navigationAction: NavigationActions, noteUid: String) {
        this.noteUid = noteUid
        this.navigationAction.value = navigationAction
    }

    override fun getNoteUid() = noteUid
}
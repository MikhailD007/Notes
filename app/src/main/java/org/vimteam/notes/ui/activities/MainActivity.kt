package org.vimteam.notes.ui.activities

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.vimteam.notes.R
import org.vimteam.notes.base.MainConstants
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.ui.adapters.NotesListAdapter
import org.vimteam.notes.ui.fragments.NoteViewFragment
import org.vimteam.notes.ui.fragments.NotesListFragment

class MainActivity : AppCompatActivity(), NotesListAdapter.ClickEventHandler {

    private var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readSettings()
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun readSettings() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        twoPane = secondFragmentContainer != null
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, NotesListFragment())
            .commitAllowingStateLoss()
    }

    override fun notesListItemClick(v: View, note: Note?) {
        if (note == null) return
        val containerId = if (twoPane) R.id.secondFragmentContainer else R.id.fragmentContainer
        supportFragmentManager.beginTransaction()
            .add(containerId, NoteViewFragment.newInstance(note))
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
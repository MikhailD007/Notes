package org.vimteam.notes.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.vimteam.notes.R
import org.vimteam.notes.ui.fragments.NotesListFragment

class MainActivity : AppCompatActivity() {

    private var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readSettings()
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun readSettings() {

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        twoPane = secondFragmentContainer != null
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, NotesListFragment())
            .commitAllowingStateLoss()
    }
}
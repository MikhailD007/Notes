package org.vimteam.notes.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.vimteam.notes.R
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.ui.adapters.NotesListAdapter
import org.vimteam.notes.ui.fragments.AboutFragment
import org.vimteam.notes.ui.fragments.NoteViewFragment
import org.vimteam.notes.ui.fragments.NotesListFragment
import org.vimteam.notes.ui.interfaces.MenuItemSelectedHandler

class MainActivity : AppCompatActivity(), NotesListAdapter.ClickEventHandler,
    MenuItemSelectedHandler {

    private var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
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
        showDetailFragment(NoteViewFragment.newInstance(note, twoPane))
    }

    override fun showAbout() {
        showDetailFragment(AboutFragment())
    }

    override fun addNewNote() {
        Toast.makeText(this, "Add new note", Toast.LENGTH_SHORT).show()
    }

    override fun editNote() {
        Toast.makeText(this, "Edit note", Toast.LENGTH_SHORT).show()
    }

    override fun deleteNote() {
        Toast.makeText(this, "Delete note", Toast.LENGTH_SHORT).show()
    }

    private fun showDetailFragment(fragment : Fragment) {
        val containerId = if (twoPane) R.id.secondFragmentContainer else R.id.fragmentContainer
        supportFragmentManager.beginTransaction()
            .add(containerId,fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
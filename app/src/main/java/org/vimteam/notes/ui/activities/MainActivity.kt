package org.vimteam.notes.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.vimteam.notes.R
import org.vimteam.notes.domain.models.Note
import org.vimteam.notes.ui.adapters.NotesListAdapter
import org.vimteam.notes.ui.fragments.AboutFragment
import org.vimteam.notes.ui.fragments.NoteEditFragment
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
        initDrawer(toolbar)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, NotesListFragment())
            .commitAllowingStateLoss()
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.aboutMenuItem -> showDetailFragment(AboutFragment())
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
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
        showDetailFragment(NoteEditFragment.newInstance(null, twoPane))
    }

    override fun editNote(note: Note) {
        Toast.makeText(this, "Edit note ${note.toString()}", Toast.LENGTH_SHORT).show()
        showDetailFragment(NoteEditFragment.newInstance(note, twoPane))
    }

    override fun deleteNote(note: Note) {
        Toast.makeText(this, "Delete note ${note.toString()}", Toast.LENGTH_SHORT).show()
    }

    private fun showDetailFragment(fragment: Fragment) {
        val containerId = if (twoPane) R.id.secondFragmentContainer else R.id.fragmentContainer
        supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
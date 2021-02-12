package org.vimteam.notes.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.models.NavigationActions
import org.vimteam.notes.domain.models.NavigationActions.*
import org.vimteam.notes.ui.fragments.*

class MainActivity : AppCompatActivity(), LocalDialogFragment.ResultHandler {

    private val navigationViewModel by viewModel<NavigationContract.ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

    override fun onDialogResult(result: NavigationActions, noteUid: String) {
        navigationViewModel.performAction(result, noteUid)
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        initDrawer(toolbar)
        navigationViewModel.twoPane = secondFragmentContainer != null
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, NotesListFragment())
            .commitAllowingStateLoss()
        setObservers()
    }

    private fun setObservers() {
        navigationViewModel.navigationAction.observe(this) {
            when (it) {
                ABOUT -> showDetailFragment(AboutFragment())
                CREATE -> showDetailFragment(NoteEditFragment.newInstance(""))
                READ -> if (navigationViewModel.getNoteUid() != "") showDetailFragment(
                    NoteViewFragment.newInstance(navigationViewModel.getNoteUid())
                )
                QUERY_DELETE -> if (navigationViewModel.getNoteUid() != "") showDialogFragment(
                    LocalDialogFragment.newInstance(
                        DELETE,
                        navigationViewModel.getNoteUid()
                    )
                )
                QUERY_UPDATE -> if (navigationViewModel.getNoteUid() != "") showDialogFragment(
                    LocalDialogFragment.newInstance(
                        UPDATE,
                        navigationViewModel.getNoteUid()
                    )
                )
                EDIT -> if (navigationViewModel.getNoteUid() != "") showDetailFragment(
                    NoteEditFragment.newInstance(navigationViewModel.getNoteUid())
                )
                else -> return@observe
            }
        }
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
                R.id.aboutMenuItem -> navigationViewModel.showAbout()
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showDetailFragment(fragment: Fragment) {
        val containerId = if (navigationViewModel.twoPane) R.id.secondFragmentContainer else R.id.fragmentContainer
        supportFragmentManager.beginTransaction()
            .add(containerId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun showDialogFragment(fragment: DialogFragment) {
        fragment.show(supportFragmentManager, "dialogTag")
    }

}
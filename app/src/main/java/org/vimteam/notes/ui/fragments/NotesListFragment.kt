package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.domain.contracts.NavigationContract
import org.vimteam.notes.domain.contracts.NotesListContract
import org.vimteam.notes.domain.models.NavigationActions.*
import org.vimteam.notes.ui.adapters.NotesListAdapter

class NotesListFragment : Fragment(), NotesListAdapter.EventHandler {

    private val notesListViewModel by viewModel<NotesListContract.ViewModel>()
    private val navigationViewModel by sharedViewModel<NavigationContract.ViewModel>()
    private val notesAdapter: NotesListAdapter = NotesListAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search: MenuItem = menu.findItem(R.id.searchMenuItem)
        val searchText: SearchView = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "Search for: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aboutMenuItem -> navigationViewModel.showAbout()
            R.id.addNoteMenuItem -> navigationViewModel.performAction(CREATE, "")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.note_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editNoteMenuItem -> navigationViewModel.performAction(EDIT, notesAdapter.getSelectedNoteUid())
            R.id.deleteNoteMenuItem -> navigationViewModel.performAction(
                QUERY_DELETE,
                notesAdapter.getSelectedNoteUid()
            )
        }
        return super.onContextItemSelected(item)
    }

    override fun registerContextMenu(v: View) {
        registerForContextMenu(v)
    }

    override fun notesListItemClick(v: View, noteUid: String) {
        navigationViewModel.performAction(READ, noteUid)
    }

    private fun initView() {
        notesListRecyclerView.adapter = notesAdapter
        setObservers()
        notesListViewModel.getNotesList()
        addNoteFloatingActionButton.setOnClickListener {
            navigationViewModel.performAction(CREATE, "")
        }
    }

    private fun setObservers() {
        notesListViewModel.notesList.observe(viewLifecycleOwner) {
            notesAdapter.notes.clear()
            notesAdapter.notes.addAll(it)
            notesAdapter.notifyDataSetChanged()
        }
        notesListViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        }
        navigationViewModel.navigationAction.observe(viewLifecycleOwner) {
            if (it == DELETE) notesListViewModel.deleteNote(notesAdapter.getSelectedNoteUid()) {
                navigationViewModel.performAction(DELETED,"")
            }
            if (it == UPDATED) notesListViewModel.getNotesList()
        }
    }
}
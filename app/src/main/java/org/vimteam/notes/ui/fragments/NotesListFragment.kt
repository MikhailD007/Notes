package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.domain.contracts.NotesContract
import org.vimteam.notes.domain.models.NavigationActions.*
import org.vimteam.notes.ui.adapters.NotesListAdapter
import org.vimteam.notes.ui.interfaces.MenuItemSelectedHandler


class NotesListFragment : Fragment(), NotesListAdapter.ContextMenuHandler {

    private val vm: NotesContract.ViewModel by viewModel()
    private val notesAdapter: NotesListAdapter = NotesListAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesListRecyclerView.adapter = notesAdapter
        vm.notesList.observe(viewLifecycleOwner) {
            notesAdapter.notes.clear()
            notesAdapter.notes.addAll(it)
            notesAdapter.notifyDataSetChanged()
        }
        vm.navigation.observe(viewLifecycleOwner) {
            when (it) {
                NONE -> null //do nothing
                CREATE -> (activity as MenuItemSelectedHandler).addNewNote()
                READ -> null //do nothing
                UPDATE -> if (vm.note.value != null) (activity as MenuItemSelectedHandler).editNote(
                    vm.note.value!!
                )
                DELETE -> {
                    notesAdapter.notes.clear()
                    notesAdapter.notes.addAll(vm.notesList.value ?: ArrayList())
                    notesAdapter.notifyDataSetChanged()
                }
                ABOUT -> (activity as MenuItemSelectedHandler).showAbout()
            }
        }
        vm.getNotesList()
        addNoteFloatingActionButton.setOnClickListener {
            vm.createNote()
        }
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
            R.id.aboutMenuItem -> vm.showAbout()
            R.id.addNoteMenuItem -> vm.createNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun registerContextMenu(v: View) {
        registerForContextMenu(v)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.note_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editNoteMenuItem -> vm.editNote(notesAdapter.getSelectedPosition())
            R.id.deleteNoteMenuItem -> vm.deleteNote(notesAdapter.getSelectedPosition())
        }
        return super.onContextItemSelected(item)
    }
}
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
import org.vimteam.notes.ui.adapters.NotesListAdapter
import org.vimteam.notes.ui.interfaces.MenuItemSelectedHandler


class NotesListFragment : Fragment() {

    private val vm: NotesContract.ViewModel by viewModel()
    private val notesAdapter: NotesListAdapter = NotesListAdapter(ArrayList())

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
        vm.getNotesList()
        addNoteFloatingActionButton.setOnClickListener {
            (activity as MenuItemSelectedHandler).addNewNote()
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
            R.id.aboutMenuItem -> (activity as MenuItemSelectedHandler).showAbout()
            R.id.addNoteMenuItem -> (activity as MenuItemSelectedHandler).addNewNote()
        }
        return super.onOptionsItemSelected(item)
    }
}
package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.notes.R
import org.vimteam.notes.domain.contracts.NotesContract
import org.vimteam.notes.ui.adapters.NotesListAdapter

class NotesListFragment: Fragment() {

    private val vm: NotesContract.ViewModel by viewModel()
    private val notesAdapter: NotesListAdapter = NotesListAdapter(ArrayList())

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
    }

}
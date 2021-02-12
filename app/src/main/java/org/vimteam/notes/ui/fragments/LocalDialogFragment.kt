package org.vimteam.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_local_dialog.*
import org.vimteam.notes.R
import org.vimteam.notes.domain.models.NavigationActions

class LocalDialogFragment : DialogFragment() {

    companion object {
        const val FURTHER_ACTION = "further_action"
        const val NOTE_KEY = "note_key"

        fun newInstance(furtherAction: NavigationActions, noteUid: String): DialogFragment {
            val dialogFragment = LocalDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(FURTHER_ACTION, furtherAction)
            bundle.putString(NOTE_KEY, noteUid)
            dialogFragment.arguments = bundle
            return dialogFragment
        }
    }

    private lateinit var noteUid: String
    private lateinit var furtherAction: NavigationActions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arguments = arguments
        if (arguments == null ||
            !arguments.containsKey(FURTHER_ACTION) ||
            !arguments.containsKey(NOTE_KEY)
        ) return
        furtherAction = arguments.get(FURTHER_ACTION) as NavigationActions
        noteUid = arguments.getString(NOTE_KEY, "")
        messageTextView.text = when (furtherAction) {
            NavigationActions.DELETE -> getString(R.string.delete_query, noteUid)
            NavigationActions.UPDATE -> getString(R.string.update_query, noteUid)
            else -> "unknown query"
        }
        noMaterialButton.setOnClickListener {
            dismiss()
        }
        yesMaterialButton.setOnClickListener {
            dismiss()
            (requireActivity() as ResultHandler).onDialogResult(furtherAction, noteUid)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    interface ResultHandler {
        fun onDialogResult(result: NavigationActions, noteUid: String)
    }

}
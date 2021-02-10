package org.vimteam.notes.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import org.vimteam.notes.data.`interface`.FirestoreDatabaseContract
import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.data.models.NoteDBListElement

class FirestoreDatabase : FirestoreDatabaseContract {

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val notesCollection = "notes"

    @Throws(Exception::class)
    override fun getNoteDBList(func: (ArrayList<NoteDBListElement>) -> Unit): ArrayList<NoteDBListElement> {
        firestoreDB.collection(notesCollection).get()
            .addOnSuccessListener {
                func.invoke(ArrayList(it.toObjects<NoteDBListElement>()))
            }
            .addOnFailureListener { throw it }
        return ArrayList()
    }

    @Throws(Exception::class)
    override fun getNoteDB(noteDBUid: String, func: (NoteDB?) -> Unit) {
        firestoreDB.collection(notesCollection).document(noteDBUid).get()
            .addOnSuccessListener {
                func.invoke(it.toObject<NoteDB>())
            }
            .addOnFailureListener { throw it }
    }

    override fun setNoteDB(noteDB: NoteDB) {
        firestoreDB.collection(notesCollection).document(noteDB.uid).set(noteDB)
            .addOnFailureListener { throw it }
    }

    override fun deleteNoteDB(noteDBUid: String) {
        firestoreDB.collection(notesCollection).document(noteDBUid).delete()
            .addOnFailureListener { throw it }
    }

}
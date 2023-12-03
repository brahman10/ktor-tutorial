package com.ep.db.dto

import com.ep.db.DatabaseConstants
import com.ep.db.DatabaseHelper
import com.ep.db.entity.Notes
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

class NoteDTO {
    private val database = DatabaseHelper.getDatabase()!!
    private val notesCollection: CoroutineCollection<Notes> = database.getCollection(DatabaseConstants.NOTES_COLLECTION_NAME)


    suspend fun addNote(notes: Notes): Notes {
        notesCollection.insertOne(notes)
        return notes
    }

    suspend fun deleteNote(id: String): Boolean {
        return notesCollection.deleteOne(Notes::id eq id).wasAcknowledged()
    }

    suspend fun updateNote(notes: Notes): Boolean {
        val note = notesCollection.updateOne(Notes::id eq notes.id, notes)
        return note.wasAcknowledged()
    }

    suspend fun getAllNotes(): List<Notes> {
        return notesCollection.find().toList()
    }

    suspend fun getNoteById(id: String): Notes? {
        return notesCollection.findOneById(id)
    }
}
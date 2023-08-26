package io.bash_psk.k_notes.domain.repository

import io.bash_psk.k_notes.data.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface NoteRepository {

    fun getNoteList() : Flow<List<NoteEntity>>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun deleteNote(objectId: ObjectId)
}
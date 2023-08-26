package io.bash_psk.k_notes.data.repository

import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.domain.repository.NoteRepository
import io.bash_psk.k_notes.presentation.utils.setLog
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class NoteRepositoryImpl(
    private val realm: Realm
) : NoteRepository {
    override fun getNoteList(): Flow<List<NoteEntity>> {

        return realm.query<NoteEntity>().asFlow().map { noteEntity: ResultsChange<NoteEntity> ->

            noteEntity.list
        }
    }

    override suspend fun insertNote(
        noteEntity: NoteEntity
    ) {

        realm.writeBlocking {

            noteEntity.title.setLog(tag = "REP-IN")

            copyToRealm(instance = noteEntity)
        }
    }

    override suspend fun updateNote(
        noteEntity: NoteEntity
    ) {

        realm.writeBlocking {

            noteEntity.title.setLog(tag = "REP-UN")

            val noteQuery = query<NoteEntity>(
                query = "_id == $0",
                noteEntity._id
            ).first().find()

            noteQuery?.title = noteEntity.title
            noteQuery?.description = noteEntity.description
            noteQuery?.timestamp = noteEntity.timestamp
        }
    }

    override suspend fun deleteNote(
        objectId: ObjectId
    ) {

        realm.writeBlocking {

            objectId.toHexString().setLog(tag = "REP-DN")

            val noteQuery = query<NoteEntity>(
                query = "_id == $0",
                objectId
            ).first().find()

            try {

                noteQuery?.let { note: NoteEntity ->

                    delete(deleteable = note)
                }
            } catch (e: Exception) {

                "${e.message}".setLog(tag =  "NOTE-REP")
            }
        }
    }

}
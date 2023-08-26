package io.bash_psk.k_notes.domain.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.data.repository.NoteRepositoryImpl
import io.bash_psk.k_notes.domain.repository.NoteRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideRealm() : Realm {

        val realmConfiguration = RealmConfiguration.Builder(
            schema = setOf(NoteEntity::class)
        ).compactOnLaunch().build()

        return Realm.open(configuration = realmConfiguration)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        realm: Realm
    ) : NoteRepository {

        return NoteRepositoryImpl(realm = realm)
    }
}
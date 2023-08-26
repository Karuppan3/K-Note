package io.bash_psk.k_notes.presentation.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.data.remote.ConstantString
import io.bash_psk.k_notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteList = MutableStateFlow(
        value = emptyList<NoteEntity>()
    )

    private val _insertTitle = MutableStateFlow(
        value = ConstantString.None
    )

    private val _insertDesc = MutableStateFlow(
        value = ConstantString.None
    )

    private val _updateId = MutableStateFlow(
        value = ConstantString.None
    )

    private val _updateTitle = MutableStateFlow(
        value = ConstantString.None
    )

    private val _updateDesc = MutableStateFlow(
        value = ConstantString.None
    )

    val noteList = _noteList.asStateFlow()
    val insertTitle = _insertTitle.asStateFlow()
    val insertDesc = _insertDesc.asStateFlow()
    val updateId = _updateId.asStateFlow()
    val updateTitle = _updateTitle.asStateFlow()
    val updateDesc = _updateDesc.asStateFlow()

    init {

        viewModelScope.launch {

            noteRepository.getNoteList().collect { noteEntities: List<NoteEntity> ->

                _noteList.update { noteEntitiesOld: List<NoteEntity> ->

                    noteEntities
                }
            }
        }
    }

    fun setInsertTitle(
        noteTitle: String
    ) {

        _insertTitle.update { titleOld: String ->

            noteTitle
        }
    }

    fun setInsertDesc(
        noteDesc: String
    ) {

        _insertDesc.update { descOld: String ->

            noteDesc
        }
    }

    fun setUpdateId(
        noteId: String
    ) {

        _updateId.update { idOld: String ->

            noteId
        }
    }

    fun setUpdateTitle(
        noteTitle: String
    ) {

        _updateTitle.update { titleOld: String ->

            noteTitle
        }
    }

    fun setUpdateDesc(
        noteDesc: String
    ) {

        _updateDesc.update { descOld: String ->

            noteDesc
        }
    }

    fun insertNote(
        noteTitle: String,
        noteDesc: String
    ) {

        viewModelScope.launch {

            when {

                noteTitle.isNotEmpty() && noteDesc.isNotEmpty() -> {

                    noteRepository.insertNote(
                        noteEntity = NoteEntity().apply {

                            title = noteTitle
                            description = noteDesc
                            timestamp = System.currentTimeMillis()
                        }
                    )
                }
            }
        }
    }

    fun updateNote(
        noteObjectId: String,
        noteTitle: String,
        noteDesc: String,
        noteTime: Long
    ) {

        viewModelScope.launch(
            context = Dispatchers.IO
        ) {

            when {

                noteTitle.isNotEmpty() && noteDesc.isNotEmpty() -> {

                    noteRepository.updateNote(
                        noteEntity = NoteEntity().apply {

                            _id = ObjectId(hexString = noteObjectId)
                            title = noteTitle
                            description = noteDesc
                            timestamp = noteTime
                        }
                    )
                }
            }
        }
    }

    fun deleteNote(
        noteObjectId: String
    ) {

        viewModelScope.launch {

            when {

                noteObjectId.isNotEmpty() -> {

                    noteRepository.deleteNote(
                        objectId = ObjectId(hexString = noteObjectId)
                    )
                }
            }
        }
    }
}
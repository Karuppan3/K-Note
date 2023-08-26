package io.bash_psk.k_notes.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.bash_psk.k_notes.presentation.screen.NoteScreen
import io.bash_psk.k_notes.presentation.theme.KNoteSTheme
import io.bash_psk.k_notes.presentation.utils.setLog

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            KNoteSTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val mainViewModel = hiltViewModel<MainViewModel>()

                    val noteList by mainViewModel.noteList.collectAsStateWithLifecycle()
                    val insertTitle by mainViewModel.insertTitle.collectAsStateWithLifecycle()
                    val insertDesc by mainViewModel.insertDesc.collectAsStateWithLifecycle()
                    val updateId by mainViewModel.updateId.collectAsStateWithLifecycle()
                    val updateTitle by mainViewModel.updateTitle.collectAsStateWithLifecycle()
                    val updateDesc by mainViewModel.updateDesc.collectAsStateWithLifecycle()

                    insertTitle.setLog("AC-IT")

                    NoteScreen(
                        noteEntities = noteList,
                        insertTitle = insertTitle,
                        insertDesc = insertDesc,
                        updateId = updateId,
                        updateTitle = updateTitle,
                        updateDesc = updateDesc,
                        setInsertTitle = mainViewModel::setInsertTitle,
                        setInsertDesc = mainViewModel::setInsertDesc,
                        setUpdateId = mainViewModel::setUpdateId,
                        setUpdateTitle = mainViewModel::setUpdateTitle,
                        setUpdateDesc = mainViewModel::setUpdateDesc,
                        onInsertNote = mainViewModel::insertNote,
                        onUpdateNote = mainViewModel::updateNote,
                        onDeleteNote = mainViewModel::deleteNote
                    )
                }
            }
        }
    }
}
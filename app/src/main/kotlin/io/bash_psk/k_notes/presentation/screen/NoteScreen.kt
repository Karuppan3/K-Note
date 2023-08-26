package io.bash_psk.k_notes.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.data.remote.ConstantString
import io.bash_psk.k_notes.presentation.note.NoteView
import io.bash_psk.k_notes.presentation.sheet.InsertNoteSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteEntities: List<NoteEntity>,
    insertTitle: String,
    insertDesc: String,
    updateId: String,
    updateTitle: String,
    updateDesc: String,
    setInsertTitle: (
        title: String
    ) -> Unit,
    setInsertDesc: (
        description: String
    ) -> Unit,
    setUpdateId: (
        objectId: String
    ) -> Unit,
    setUpdateTitle: (
        title: String
    ) -> Unit,
    setUpdateDesc: (
        description: String
    ) -> Unit,
    onInsertNote: (
        title: String,
        desc: String
    ) -> Unit,
    onUpdateNote: (
        noteObjectId: String,
        noteTitle: String,
        noteDesc: String,
        noteTime: Long
    ) -> Unit,
    onDeleteNote: (
        noteObjectId: String
    ) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val insertNoteSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val isExpandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    InsertNoteSheet(
        sheetState = insertNoteSheetState,
        onDismiss = {

            coroutineScope.launch {

                insertNoteSheetState.hide()
            }
        },
        insertTitle = insertTitle,
        insertDesc = insertDesc,
        setInsertTitle = setInsertTitle,
        setInsertDesc = setInsertDesc,
        onInsertNote = onInsertNote
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {

            ExtendedFloatingActionButton(
                expanded = isExpandedFab,
                icon = {

                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = ConstantString.InsertNoteButton
                    )
                },
                text = {

                    Text(text = ConstantString.InsertNoteButton)
                },
                onClick = {

                    coroutineScope.launch {

                        insertNoteSheetState.expand()
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues: PaddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {

            items(
                items = noteEntities
            ) { noteEntity: NoteEntity ->

                NoteView(
                    noteEntity = noteEntity,
                    updateId = updateId,
                    updateTitle = updateTitle,
                    updateDesc = updateDesc,
                    setUpdateId = setUpdateId,
                    setUpdateTitle = setUpdateTitle,
                    setUpdateDesc = setUpdateDesc,
                    onUpdateNote = onUpdateNote,
                    onDeleteNote = onDeleteNote
                )
            }
        }
    }
}
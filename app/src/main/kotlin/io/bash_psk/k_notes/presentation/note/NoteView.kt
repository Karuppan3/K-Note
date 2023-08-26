package io.bash_psk.k_notes.presentation.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.presentation.sheet.ViewNoteSheet
import io.bash_psk.k_notes.presentation.utils.shortDateFormat
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(
    noteEntity: NoteEntity,
    updateId: String,
    updateTitle: String,
    updateDesc: String,
    setUpdateId: (
        objectId: String
    ) -> Unit,
    setUpdateTitle: (
        title: String
    ) -> Unit,
    setUpdateDesc: (
        description: String
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
    val viewNoteSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ViewNoteSheet(
        sheetState = viewNoteSheetState,
        onDismiss = {

            coroutineScope.launch {

                viewNoteSheetState.hide()
            }
        },
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

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = {

            coroutineScope.launch {

                viewNoteSheetState.expand()
            }
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    paddingValues = PaddingValues(
                        vertical = 16.dp,
                        horizontal = 16.dp
                    )
                ),
            verticalArrangement = Arrangement
                .spacedBy(space = 4.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = noteEntity.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = noteEntity.description,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = noteEntity.timestamp.shortDateFormat(),
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
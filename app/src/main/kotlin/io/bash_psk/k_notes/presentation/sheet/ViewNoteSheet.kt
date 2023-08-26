package io.bash_psk.k_notes.presentation.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.bash_psk.k_notes.data.entities.NoteEntity
import io.bash_psk.k_notes.data.remote.ConstantString
import io.bash_psk.k_notes.presentation.utils.longDateFormat
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
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
    val updateNoteSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    UpdateNoteSheet(
        sheetState = updateNoteSheetState,
        onDismiss = {

            coroutineScope.launch {

                updateNoteSheetState.hide()
            }
        },
        updateId = updateId,
        updateTitle = updateTitle,
        updateDesc = updateDesc,
        setUpdateTitle = setUpdateTitle,
        setUpdateDesc = setUpdateDesc,
        onUpdateNote = onUpdateNote
    )

    AnimatedVisibility(
        visible = sheetState.isVisible
    ) {

        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            shape = MaterialTheme.shapes.small,
            onDismissRequest = onDismiss
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        paddingValues = PaddingValues(
                            vertical = 8.dp,
                            horizontal = 8.dp
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = noteEntity.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = noteEntity.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = noteEntity.timestamp.longDateFormat(),
                    style = MaterialTheme.typography.labelMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Button(
                        onClick = {

                            onDeleteNote(noteEntity._id.toHexString())
                            onDismiss()
                        }
                    ) {

                        Text(
                            text = ConstantString.DeleteNoteButton
                        )
                    }

                    Button(
                        onClick = {

                            setUpdateId(noteEntity._id.toHexString())
                            setUpdateTitle(noteEntity.title)
                            setUpdateDesc(noteEntity.description)

                            coroutineScope.launch {

                                updateNoteSheetState.expand()
                            }
                        }
                    ) {

                        Text(
                            text = ConstantString.UpdateNoteButton
                        )
                    }
                }
            }
        }
    }
}
package io.bash_psk.k_notes.presentation.sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.bash_psk.k_notes.data.remote.ConstantString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    updateId: String,
    updateTitle: String,
    updateDesc: String,
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
    ) -> Unit
) {

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = updateTitle,
                    onValueChange = setUpdateTitle,
                    placeholder = {

                        Text(
                            text = ConstantString.EnterTitle
                        )
                    },
                    label = {

                        Text(
                            text = ConstantString.EnterTitle
                        )
                    }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = updateDesc,
                    onValueChange = setUpdateDesc,
                    placeholder = {

                        Text(
                            text = ConstantString.EnterDesc
                        )
                    },
                    label = {

                        Text(
                            text = ConstantString.EnterDesc
                        )
                    }
                )

                Button(
                    onClick = {

                        onUpdateNote(
                            updateId,
                            updateTitle,
                            updateDesc,
                            System.currentTimeMillis()
                        )

                        onDismiss()
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
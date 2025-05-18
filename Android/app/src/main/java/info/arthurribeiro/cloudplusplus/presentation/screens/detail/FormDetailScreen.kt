package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import info.arthurribeiro.cloudplusplus.data.model.responses.Field

@Composable
fun FormDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FormDetailViewModel,
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState

    val currentSection = uiState.currentSectionIndex + 1
    val listState = rememberLazyListState()

    val coroutineState = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {

        IconButton(
            onClick = {
                onClose()
            }
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close")
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                uiState.title,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp),
                progress = { currentSection.toFloat() / uiState.totalSections },
            )

            Text(
                text = "Section $currentSection of ${uiState.totalSections}",
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }


        Surface(
            modifier = Modifier.padding(top = 24.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                state = listState
            ) {
                items(
                    items = uiState.items,
                    key = { it.field.id },
                ) { item ->
                    when (item.field) {
                        is Field.TextField, is Field.Number -> {
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(text = item.field.label)
                                OutlinedTextField(
                                    value = item.value,
                                    onValueChange = { newValue ->
                                        viewModel.onDropDownSelected(
                                            fieldId = item.field.id,
                                            value = newValue
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = if (item.field is Field.Number) {
                                            KeyboardType.Number
                                        } else {
                                            KeyboardType.Text
                                        }
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        is Field.Description -> {
                            AndroidView(
                                factory = { context ->
                                    TextView(context).apply {
                                        text = HtmlCompat.fromHtml(
                                            item.field.label,
                                            HtmlCompat.FROM_HTML_MODE_COMPACT
                                        )
                                        textSize = 16f
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }

                        is Field.Dropdown -> {
                            var expanded by rememberSaveable { mutableStateOf(false) }
                            val selected = rememberSaveable(item.value) {
                                item.field.options.find { it.value == item.value }?.label ?: ""
                            }

                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(text = item.field.label)
                                Box {
                                    OutlinedTextField(
                                        value = selected,
                                        onValueChange = {},
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { expanded = true },
                                        readOnly = true,
                                        enabled = false,
                                        trailingIcon = {
                                            Icon(
                                                Icons.Default.ArrowDropDown,
                                                contentDescription = null
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors().copy(
                                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                            disabledIndicatorColor = MaterialTheme.colorScheme.outline,
                                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        item.field.options.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option.label) },
                                                onClick = {
                                                    viewModel.onValueChange(
                                                        item.field.id,
                                                        option.value
                                                    )
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(32.dp))
                    Row {

                        if (currentSection > 0 && currentSection < uiState.totalSections) {
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.onPreviousSectionClick()
                                }
                            ) {
                                Text(
                                    text = "Back",
                                )
                            }
                        }

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (currentSection < uiState.totalSections) {
                                    viewModel.onNextSectionClick()
                                } else {
                                    onClose()
                                }
                            }
                        ) {
                            Text(
                                text = if (currentSection < uiState.totalSections) {
                                    "Next"
                                } else {
                                    "Finish"
                                }
                            )
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

package info.arthurribeiro.cloudplusplus.presentation.screens.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.presentation.screens.FormDetailDestination
import info.arthurribeiro.cloudplusplus.presentation.theme.CloudPlusPlusTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FormsScreen(
    navigate: (formId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FormsViewModel
) {

    val uiState by viewModel.uiState

    CloudPlusPlusTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.createNewForm()
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Form")
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            LazyColumn(
                modifier = modifier.padding(paddingValues)
            ) {
                items(
                    items = uiState.forms,
                    key = { it.id }
                ) { form ->
                    Button(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        onClick = {
                            navigate(form.id)
                        }
                    ) {
                        Text(
                            text = form.id,
                        )
                    }
                }
            }
        }
    }
}
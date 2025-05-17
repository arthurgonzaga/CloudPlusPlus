package info.arthurribeiro.cloudplusplus.presentation.screens.structures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.presentation.screens.FormsDestination
import info.arthurribeiro.cloudplusplus.presentation.theme.CloudPlusPlusTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StructuresScreen(
    navigate: (FormsDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StructuresViewModel = koinViewModel()
) {
    val uiState by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getStructures()
    }

    CloudPlusPlusTheme {
        LazyColumn(
            modifier = modifier.systemBarsPadding(),
        ) {
            items(uiState.list) { structure ->
                Button(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    onClick = {
                        navigate(FormsDestination(structure))
                    }
                ) {
                    Text(
                        text = structure.name,
                    )
                }
            }
        }
    }
}
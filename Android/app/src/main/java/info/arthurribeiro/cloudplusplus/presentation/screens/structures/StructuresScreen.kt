package info.arthurribeiro.cloudplusplus.presentation.screens.structures

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun StructuresScreen(
    modifier: Modifier = Modifier,
    viewModel: StructuresViewModel = koinViewModel()
) {
    val uiState by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getStructures()
    }

    LazyColumn(
        modifier = modifier.systemBarsPadding(),
    ) {

        items(uiState.list) { structure ->
            Column {
                Text(
                    text = "${structure.name}",
                )
            }
        }
    }
}
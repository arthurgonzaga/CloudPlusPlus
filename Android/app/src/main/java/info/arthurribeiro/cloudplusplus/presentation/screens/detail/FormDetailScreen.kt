package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import info.arthurribeiro.cloudplusplus.presentation.screens.forms.FormsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FormDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FormDetailViewModel,
) {

    LaunchedEffect(Unit) {
        viewModel.getSection()
    }
}
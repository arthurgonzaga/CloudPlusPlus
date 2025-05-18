package info.arthurribeiro.cloudplusplus.presentation.screens.structures

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.arthurribeiro.cloudplusplus.data.repository.FormRepository
import kotlinx.coroutines.launch

class StructuresViewModel(
    private val repository: FormRepository
): ViewModel() {

    private val _state = mutableStateOf(StructuresUiState())
    val state: State<StructuresUiState> = _state

    fun getStructures() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val list = repository.getStructures()
        _state.value = _state.value.copy(
            list = list,
            isLoading = false
        )
    }
}
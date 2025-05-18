package info.arthurribeiro.cloudplusplus.presentation.screens.forms

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.repository.FormRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FormsViewModel(
    private val repository: FormRepository,
    private val structure: FormStructure
) : ViewModel() {

    private val _uiState = mutableStateOf(FormsState())
    val uiState: State<FormsState> = _uiState

    init {
        repository.getForms(structure.id)
            .onEach { forms ->
                Log.i("FormsViewModel", "Forms: ${forms.size}")
                _uiState.value = _uiState.value.copy(
                    forms = forms,
                    isLoading = false,
                    backgroundColor = structure.getBackgroundColor(),
                    textColor = structure.getTextColor()
                )
            }
            .launchIn(viewModelScope)
    }

    fun createNewForm() = viewModelScope.launch {
        repository.createForm(structure.id)
    }
}
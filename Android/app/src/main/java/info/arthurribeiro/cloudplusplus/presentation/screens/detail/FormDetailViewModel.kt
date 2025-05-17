package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.repository.FormRepository
import kotlinx.coroutines.launch

class FormDetailViewModel(
    private val repository: FormRepository,
    private val formId: String,
    private val structure: FormStructure,
) : ViewModel() {

    fun getSection() {
        viewModelScope.launch {
            val section = repository.getSection(structureId = structure.id, index = 0)
            Log.i("FormDetailViewModel", "Section: ${section.fields}")
        }
    }
}
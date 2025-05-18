package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.model.responses.Field
import info.arthurribeiro.cloudplusplus.data.repository.FormRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FormDetailViewModel(
    private val repository: FormRepository,
    private val json: Json,
    private val formId: String,
    private val structure: FormStructure,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        FormDetailState(
            title = structure.name,
            currentSectionIndex = 0
        )
    )
    val uiState: State<FormDetailState> = _uiState

    private lateinit var currentSection: FormSection

    init {
        getSection(0)
    }

    fun onNextSectionClick() {
        if (_uiState.value.currentSectionIndex + 1 > _uiState.value.totalSections) return
        val nextIndex = _uiState.value.currentSectionIndex + 1
        if (nextIndex < structure.totalFields) {
            getSection(nextIndex)
        }
    }

    fun onPreviousSectionClick() {
        val previousIndex = _uiState.value.currentSectionIndex - 1
        if (previousIndex >= 0) {
            getSection(previousIndex)
        }
    }

    fun onDropDownSelected(fieldId: String, value: String) {
        updateValueOnUiState(fieldId, value)
    }

    private fun getSection(index: Int = 0) {
        viewModelScope.launch {
            val totalSections = repository.countSections(structureId = structure.id)
            currentSection = repository.getSection(structureId = structure.id, index = index)

            val fields: List<Field> = json.decodeFromString(currentSection.fields)
            val fieldsValues = repository.getFieldValues(formId = formId, sectionId = currentSection.id)

            val list: List<FormDetailState.Item> = fields.map { field ->
                val value = fieldsValues.find { it.id == field.id }?.value ?: ""
                FormDetailState.Item(field = field, value = value)
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                currentSectionTitle = currentSection.title,
                totalSections = totalSections,
                currentSectionIndex = index,
                items = list
            )
        }
    }

    fun onValueChange(fieldId: String, value: String) {
        updateValueOnUiState(fieldId, value)
    }

    private fun updateValueOnUiState(fieldId: String, value: String) {
        _uiState.value = _uiState.value.copy(
            items = _uiState.value.items.toMutableList().apply {
                val index = indexOfFirst { it.field.id == fieldId }
                if (index != -1) {
                    this[index] = this[index].copy(value = value)
                }
            }
        )

        viewModelScope.launch {
            repository.saveFieldValue(
                field = FieldValue(
                    id = fieldId,
                    value = value,
                    formId = formId,
                    sectionId = currentSection.id,
                )
            )
        }
    }
}
package info.arthurribeiro.cloudplusplus.presentation.screens.structures

import android.os.Parcelable
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure

data class StructuresUiState(
    val list: List<FormStructure> = emptyList(),
    val isLoading: Boolean = true,
)

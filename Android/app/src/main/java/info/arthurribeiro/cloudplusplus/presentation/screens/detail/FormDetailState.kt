package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import info.arthurribeiro.cloudplusplus.data.model.responses.Field

data class FormDetailState(
    val isLoading: Boolean = true,
    val title: String,
    val currentSectionIndex: Int,
    val totalSections: Int = 0,
    val currentSectionTitle: String = "",
    val items: List<Item> = emptyList(),
) {
    data class Item(
        val field: Field,
        val value: String,
    )
}
package info.arthurribeiro.cloudplusplus.presentation.screens.detail

import info.arthurribeiro.cloudplusplus.data.model.responses.Field

data class FormDetailState(
    val isLoading: Boolean = true,
    val items: List<Item> = emptyList(),
) {
    class Item(
        val field: Field,
        val value: String,
    )
}
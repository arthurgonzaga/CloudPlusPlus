package info.arthurribeiro.cloudplusplus.presentation.screens.forms

import info.arthurribeiro.cloudplusplus.data.model.entity.Form

data class FormsState(
    val isLoading: Boolean = true,
    val forms: List<Form> = emptyList(),
)

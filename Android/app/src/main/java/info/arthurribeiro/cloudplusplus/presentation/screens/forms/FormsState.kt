package info.arthurribeiro.cloudplusplus.presentation.screens.forms

import androidx.compose.ui.graphics.Color
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkGreen
import info.arthurribeiro.cloudplusplus.presentation.theme.LightGreen

data class FormsState(
    val isLoading: Boolean = true,
    val backgroundColor: Color = LightGreen,
    val textColor: Color = DarkGreen,
    val forms: List<Form> = emptyList(),
)

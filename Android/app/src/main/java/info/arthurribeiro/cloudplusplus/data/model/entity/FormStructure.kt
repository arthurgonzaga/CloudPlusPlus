package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkGreen
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkRed
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkYellow
import info.arthurribeiro.cloudplusplus.presentation.theme.LightGreen
import info.arthurribeiro.cloudplusplus.presentation.theme.LightRed
import info.arthurribeiro.cloudplusplus.presentation.theme.LightYellow
import kotlinx.serialization.Serializable

@Entity(tableName = "form_structures")
@Serializable
data class FormStructure(
    @PrimaryKey val id: String,
    val name: String,
    val totalFields: Int,
) {

    fun getBackgroundColor(): Color {
        return when (totalFields) {
            in 0..100 -> LightGreen
            in 200..500 -> LightYellow
            else -> LightRed
        }
    }

    fun getTextColor(): Color {
        return when (totalFields) {
            in 0..100 -> DarkGreen
            in 101..500 -> DarkYellow
            else -> DarkRed
        }
    }
}
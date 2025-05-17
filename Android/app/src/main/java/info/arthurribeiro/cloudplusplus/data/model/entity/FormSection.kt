package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "form_sections")
@Serializable
data class FormSection(
    @PrimaryKey val id: String,
    val structureId: String,
    val index: Int,
    val fields: String,
)

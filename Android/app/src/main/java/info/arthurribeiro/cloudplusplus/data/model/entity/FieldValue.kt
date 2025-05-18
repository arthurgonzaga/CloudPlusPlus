package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "form_field_values")
@Serializable
data class FieldValue(
    @PrimaryKey val id: String,
    val formId: String,
    val sectionId: String,
    val value: String,
)

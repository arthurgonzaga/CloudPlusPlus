package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "fields")
@Serializable
data class FieldValue(
    @PrimaryKey val id: String,
    val formId: Long,
    val sectionId: String,
    val type: String,
    val value: String,
)

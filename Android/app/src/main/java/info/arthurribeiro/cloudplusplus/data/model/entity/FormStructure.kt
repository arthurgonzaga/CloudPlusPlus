package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "structures")
data class FormStructure(
    @PrimaryKey val id: String,
    val name: String,
    val totalFields: Int,
    val json: String
)

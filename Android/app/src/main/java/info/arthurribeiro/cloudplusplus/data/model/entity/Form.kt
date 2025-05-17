package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "forms")
@Serializable
data class Form(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val structureId: Long
)

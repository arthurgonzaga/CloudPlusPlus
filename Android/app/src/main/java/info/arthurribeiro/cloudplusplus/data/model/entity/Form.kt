package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(tableName = "forms")
@Serializable
data class Form(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val structureId: Long
)

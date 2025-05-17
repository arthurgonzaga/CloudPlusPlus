package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forms")
data class Form(
    @PrimaryKey(autoGenerate = true) val id: Long = -1L,
    val structureId: String
)

package info.arthurribeiro.cloudplusplus.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import info.arthurribeiro.cloudplusplus.data.model.responses.FormResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "structures")
@Serializable
data class FormStructure(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val totalFields: Int,
    val json: String
) {

    fun getResponse(json: Json): FormResponse {
        return json.decodeFromString(this.json)
    }
}

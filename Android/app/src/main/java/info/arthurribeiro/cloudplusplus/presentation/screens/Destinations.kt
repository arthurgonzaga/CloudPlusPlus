package info.arthurribeiro.cloudplusplus.presentation.screens

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
object StructuresDestination

@Serializable
data class FormsDestination(val structure: FormStructure)

@Serializable
data class FormDetailDestination(val formId: String, val structure: FormStructure)

class JsonNavType<T : Any>(
    private val serializer: kotlinx.serialization.KSerializer<T>,
    isNullableAllowed: Boolean = false
) : NavType<T>(isNullableAllowed) {
    override fun put(bundle: SavedState, key: String, value: T) {
        val encodedValue = Uri.encode(Json.encodeToString(serializer, value))
        bundle.putString(key, encodedValue)
    }

    override fun get(bundle: SavedState, key: String): T? {
        val value = bundle.getString(key) ?: return null
        return Json.decodeFromString(serializer, Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, Uri.decode(value))
    }
}
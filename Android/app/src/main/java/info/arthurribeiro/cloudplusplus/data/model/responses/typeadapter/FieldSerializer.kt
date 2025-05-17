package info.arthurribeiro.cloudplusplus.data.model.responses.typeadapter

import info.arthurribeiro.cloudplusplus.data.model.responses.Field
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object FieldSerializer : JsonContentPolymorphicSerializer<Field>(Field::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Field> {
        return when (element.jsonObject.getValue("type").jsonPrimitive.content) {
            "text" -> Field.TextField.serializer()
            "number" -> Field.Number.serializer()
            "description" -> Field.Description.serializer()
            "dropdown" -> Field.Dropdown.serializer()
            else -> Field.TextField.serializer()
        }
    }
}
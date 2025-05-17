package info.arthurribeiro.cloudplusplus.data.mapper

import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.model.responses.Section
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.UUID

object Mapper {

    fun getFormStructureList(structureStringList: List<String>): List<FormStructure> {
        return structureStringList.map { structureJson ->
            val responseJsonObject = Json.parseToJsonElement(structureJson).jsonObject

            val structureId = UUID.randomUUID().toString()

            FormStructure(
                id = structureId,
                name = responseJsonObject["title"]!!.jsonPrimitive.content,
                totalFields = responseJsonObject["fields"]!!.jsonArray.size,
            )
        }
    }

    fun getFormSectionList(json: String, structureId: String): List<FormSection> {
        val responseJsonObject = Json.parseToJsonElement(json).jsonObject

        //how many sections
        val sections = Json.decodeFromJsonElement(
            ListSerializer(Section.serializer()),
            responseJsonObject["sections"]!!
        )

        return sections.map { section ->
            val id = section.id

            val sublist = responseJsonObject["fields"]
                ?.jsonArray
                ?.toMutableList()
                ?.subList(section.from, section.to)
                ?.toList()

            val fieldsJson = Json.encodeToString(sublist)

            id to fieldsJson
        }.mapIndexed { index, (sectionId, fields) ->
            FormSection(
                id = sectionId,
                index = index,
                structureId = structureId,
                fields = fields
            )
        }
    }
}
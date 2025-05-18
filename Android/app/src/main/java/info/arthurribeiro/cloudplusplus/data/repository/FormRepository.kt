package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    suspend fun getStructures(): List<FormStructure>

    fun getForms(structureId: String): Flow<List<Form>>
    suspend fun createForm(structureId: String)

    suspend fun countSections(structureId: String): Int
    suspend fun getSection(structureId: String, index: Int): FormSection

    suspend fun getFieldValues(formId: String, sectionId: String): List<FieldValue>
    suspend fun saveFieldValue(field: FieldValue)

}
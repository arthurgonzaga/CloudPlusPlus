package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    suspend fun getStructures(): List<FormStructure>

    fun getForms(structureId: Long): Flow<List<Form>>
    suspend fun createForm(structureId: Long)

    fun getFields(formId: Long, sectionId: String): Flow<List<FieldValue>>
    suspend fun saveField(formId: Long, sectionId: String, field: FieldValue)

}
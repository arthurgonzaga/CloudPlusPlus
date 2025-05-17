package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.coroutines.flow.Flow

interface FormRepository {
    suspend fun getStructures(): List<FormStructure>

    fun getForms(): Flow<List<Form>>
    suspend fun createForm(structure: FormStructure)

    fun getFields(sectionId: String): Flow<List<FieldValue>>
    suspend fun saveField(sectionId: String, field: FieldValue)

}
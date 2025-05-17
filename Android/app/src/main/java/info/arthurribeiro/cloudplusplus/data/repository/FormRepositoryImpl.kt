package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.datasource.local.FormDatabase
import info.arthurribeiro.cloudplusplus.data.datasource.remote.FormService
import info.arthurribeiro.cloudplusplus.data.mapper.Mapper
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.UUID

class FormRepositoryImpl(
    private val remoteDataSource: FormService,
    private val localDataSource: FormDatabase,
    private val json: Json
) : FormRepository {

    override suspend fun getStructures(): List<FormStructure> {
        var formStructureList = localDataSource.formStructureDao().getAll()

        if (formStructureList.isEmpty()) {
            // Fetch from remote
            val structureResponseList = remoteDataSource.getStructures()

            // Save structure to local database
            formStructureList = Mapper.getFormStructureList(structureResponseList)
            localDataSource.formStructureDao().addAll(*formStructureList.toTypedArray())

            // Save sections to local database
            structureResponseList.forEachIndexed { index, responseItem ->
                val structureId = formStructureList[index].id
                val sectionList = Mapper.getFormSectionList(responseItem, structureId)
                localDataSource.sectionDao().addAll(*sectionList.toTypedArray())
            }
        }

        return formStructureList
    }


    override fun getForms(structureId: String): Flow<List<Form>> =
        localDataSource.formDao().getAll(structureId)

    override suspend fun createForm(structureId: String) {
        return localDataSource.formDao().upsert(Form(structureId = structureId))
    }

    override suspend fun getSection(structureId: String, index: Int): FormSection {
        return localDataSource.sectionDao().getFormSection(structureId, index)
    }

    override fun getFieldValues(formId: Long, sectionId: String): Flow<List<FieldValue>> {
        return localDataSource.fieldValueDao().getAllFrom(formId, sectionId)
    }

    override suspend fun saveFieldValue(field: FieldValue) {
        localDataSource.fieldValueDao().upsert(field)
    }
}
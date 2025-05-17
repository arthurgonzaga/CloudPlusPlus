package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.datasource.local.FieldValueDao
import info.arthurribeiro.cloudplusplus.data.datasource.local.FormDao
import info.arthurribeiro.cloudplusplus.data.datasource.local.FormStructureDao
import info.arthurribeiro.cloudplusplus.data.datasource.remote.FormService
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.model.responses.FormResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import java.util.UUID
import kotlin.uuid.Uuid

class FormRepositoryImpl(
    private val formService: FormService,
    private val formStructureDao: FormStructureDao,
    private val formDao: FormDao,
    private val fieldValueDao: FieldValueDao,
    private val json: Json
) : FormRepository {

    override suspend fun getStructure(): List<FormStructure> {
        var structures = formStructureDao.getAll()

        if (structures.isEmpty()) {
            val remoteStructures = formService.getFormStructures()
            structures = remoteStructures.map { structure ->
                FormStructure(
                    id = UUID.randomUUID().toString(),
                    name = structure.title,
                    json = json.encodeToString(FormResponse.serializer(), structure),
                    totalFields = structure.fields.size,
                )
            }
            formStructureDao.addAll(*structures.toTypedArray())
        }

        return structures
    }

    override fun getForms(): Flow<List<Form>> = formDao.getAll()

    override suspend fun createForm(structure: FormStructure): Form {
        return formDao.add(Form(structureId = structure.id))
    }

    override fun getFields(sectionId: String): Flow<List<FieldValue>> {
        return fieldValueDao.getAllFrom(sectionId)
    }

    override suspend fun saveField(sectionId: String, field: FieldValue) {
        fieldValueDao.upsert(field)
    }
}
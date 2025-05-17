package info.arthurribeiro.cloudplusplus.data.repository

import info.arthurribeiro.cloudplusplus.data.datasource.local.FormDatabase
import info.arthurribeiro.cloudplusplus.data.datasource.remote.FormService
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.data.model.responses.FormResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class FormRepositoryImpl(
    private val remoteDataSource: FormService,
    private val localDataSource: FormDatabase,
    private val json: Json
) : FormRepository {

    override suspend fun getStructures(): List<FormStructure> {
        var structures = localDataSource.formStructureDao().getAll()

        if (structures.isEmpty()) {
            val remoteStructures = remoteDataSource.getFormStructures()
            structures = remoteStructures.map { structure ->
                FormStructure(
                    name = structure.title,
                    json = json.encodeToString(FormResponse.serializer(), structure),
                    totalFields = structure.fields.size,
                )
            }
            localDataSource.formStructureDao().addAll(*structures.toTypedArray())
        }

        return structures
    }

    override fun getForms(): Flow<List<Form>> = localDataSource.formDao().getAll()

    override suspend fun createForm(structure: FormStructure) {
        return localDataSource.formDao().upsert(Form(structureId = structure.id))
    }

    override fun getFields(sectionId: String): Flow<List<FieldValue>> {
        return localDataSource.fieldValueDao().getAllFrom(sectionId)
    }

    override suspend fun saveField(sectionId: String, field: FieldValue) {
        localDataSource.fieldValueDao().upsert(field)
    }
}
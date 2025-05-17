package info.arthurribeiro.cloudplusplus.data.datasource.remote

import android.content.Context
import info.arthurribeiro.cloudplusplus.data.model.responses.FormResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MockedFormService(
    private val context: Context,
    private val json: Json,
) : FormService {

    override suspend fun getFormStructures(): List<FormResponse> {
        return withContext(Dispatchers.IO) {
            listOf(
                get("all-fields.json"),
                get("200-form.json"),
            )
        }
    }


    private fun get(name: String): FormResponse {
        val inputStream = context.assets.open(name)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return json.decodeFromString(jsonString)
    }

}
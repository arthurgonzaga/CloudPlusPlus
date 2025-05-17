package info.arthurribeiro.cloudplusplus.data.datasource.remote

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MockedFormService(
    private val context: Context,
    private val json: Json,
) : FormService {

    override suspend fun getStructures(): List<String> {
        return withContext(Dispatchers.IO) {
            listOf(
                get("all-fields.json"),
                get("200-form.json"),
            )
        }
    }


    private fun get(name: String): String {
        val inputStream = context.assets.open(name)
        return inputStream.bufferedReader().use { it.readText() }
    }

}
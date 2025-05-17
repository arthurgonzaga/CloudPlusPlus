package info.arthurribeiro.cloudplusplus.data.datasource.remote

interface FormService {

    suspend fun getStructures(): List<String>
}
package info.arthurribeiro.cloudplusplus.data.datasource.remote

import info.arthurribeiro.cloudplusplus.data.model.responses.FormResponse

interface FormService {

    suspend fun getFormStructures(): List<FormResponse>
}
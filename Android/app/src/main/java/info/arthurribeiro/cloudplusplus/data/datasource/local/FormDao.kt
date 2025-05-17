package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import kotlinx.coroutines.flow.Flow

@Dao
interface FormDao {

    @Upsert
    suspend fun upsert(vararg form: Form)

    @Query("SELECT * FROM forms")
    fun getAll(): Flow<List<Form>>
}
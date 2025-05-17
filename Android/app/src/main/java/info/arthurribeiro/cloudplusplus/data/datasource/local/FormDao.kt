package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import kotlinx.coroutines.flow.Flow

@Dao
interface FormDao {

    @Insert
    suspend fun add(vararg form: Form): Form

    @Query("SELECT * FROM forms")
    fun getAll(): Flow<List<Form>>
}
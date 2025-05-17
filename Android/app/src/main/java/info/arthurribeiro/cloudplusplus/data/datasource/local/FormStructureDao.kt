package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure

@Dao
interface FormStructureDao {

    @Insert
    suspend fun addAll(vararg structure: FormStructure)

    @Query("SELECT * FROM structures")
    suspend fun getAll(): List<FormStructure>
}
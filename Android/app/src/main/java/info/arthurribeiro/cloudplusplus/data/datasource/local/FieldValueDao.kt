package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldValueDao {

    @Upsert
    fun upsert(vararg field: FieldValue)

    @Query("SELECT * FROM fields WHERE sectionId = :section")
    fun getAllFrom(section: String): Flow<List<FieldValue>>
}
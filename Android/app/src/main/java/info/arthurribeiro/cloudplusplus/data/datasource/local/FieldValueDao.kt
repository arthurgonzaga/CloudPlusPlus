package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldValueDao {

    @Upsert
    fun upsert(field: FieldValue)

    @Query("SELECT * FROM fields WHERE formId = :formId AND sectionId = :section")
    fun getAllFrom(formId: Long, section: String): Flow<List<FieldValue>>
}
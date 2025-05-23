package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldValueDao {

    @Upsert
    suspend fun upsert(field: FieldValue)

    @Query("SELECT * FROM form_field_values WHERE formId = :formId AND sectionId = :section")
    suspend fun getAllFrom(formId: String, section: String): List<FieldValue>
}
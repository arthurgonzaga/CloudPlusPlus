package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure

@Dao
interface FormSectionDao {

    @Insert
    suspend fun addAll(vararg structure: FormSection)

    @Query("SELECT * FROM form_sections WHERE structureId = :structureId")
    suspend fun getAllFrom(structureId: String): List<FormSection>

    @Query("SELECT * FROM form_sections WHERE structureId = :structureId AND `index` = :index")
    suspend fun getFormSection(structureId: String, index: Int): FormSection

    @Query("SELECT COUNT(*) FROM form_sections WHERE structureId = :structureId")
    suspend fun countSections(structureId: String): Int
}
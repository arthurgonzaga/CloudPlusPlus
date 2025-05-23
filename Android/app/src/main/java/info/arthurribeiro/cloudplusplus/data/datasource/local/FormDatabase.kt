package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormSection
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure

@Database(entities = [FormStructure::class, Form::class, FieldValue::class, FormSection::class], version = 1)
abstract class FormDatabase : RoomDatabase() {
    abstract fun formStructureDao(): FormStructureDao
    abstract fun formDao(): FormDao
    abstract fun fieldValueDao(): FieldValueDao
    abstract fun sectionDao(): FormSectionDao
}
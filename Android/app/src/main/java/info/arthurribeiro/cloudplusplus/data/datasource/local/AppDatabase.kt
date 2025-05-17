package info.arthurribeiro.cloudplusplus.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import info.arthurribeiro.cloudplusplus.data.model.entity.FieldValue
import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure

@Database(entities = [FormStructure::class, Form::class, FieldValue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
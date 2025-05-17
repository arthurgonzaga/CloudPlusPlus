package info.arthurribeiro.cloudplusplus.data

import androidx.room.Room
import info.arthurribeiro.cloudplusplus.data.datasource.local.FormDatabase
import info.arthurribeiro.cloudplusplus.data.datasource.remote.FormService
import info.arthurribeiro.cloudplusplus.data.datasource.remote.MockedFormService
import info.arthurribeiro.cloudplusplus.data.repository.FormRepository
import info.arthurribeiro.cloudplusplus.data.repository.FormRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<FormDatabase> {
        Room.databaseBuilder(
            context = androidApplication(),
            FormDatabase::class.java,
            name = "form-database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
        }
    }

    single<FormService> {
        MockedFormService(
            context = get(),
            json = get()
        )
    }

    single<FormRepository> {
        FormRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            json = get()
        )
    }
}
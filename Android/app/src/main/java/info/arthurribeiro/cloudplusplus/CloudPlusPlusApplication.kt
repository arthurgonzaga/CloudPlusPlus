package info.arthurribeiro.cloudplusplus

import android.app.Application
import info.arthurribeiro.cloudplusplus.data.dataModule
import info.arthurribeiro.cloudplusplus.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CloudPlusPlusApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CloudPlusPlusApplication)
            modules(
                dataModule,
                presentationModule
            )
        }
    }
}
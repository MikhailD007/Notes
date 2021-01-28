package org.vimteam.notes

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val crashlitics = FirebaseCrashlytics.getInstance()
        with(crashlitics) {
            setUserId("")
            setCustomKey("UserName", "")
        }
        startKoin {
            androidContext(this@App)
            modules(
                MainModule.get()
            )
        }
    }

}
package com.testing.miniproject

import android.app.Application
import com.testing.miniproject.config.databaseModule
import com.testing.miniproject.config.migration.MigrationVersion
import com.testing.miniproject.config.remoteModule
import com.testing.miniproject.config.repositoryModule
import com.testing.miniproject.config.uiModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {
    private val appModules by lazy {
        listOf(remoteModule, repositoryModule, databaseModule, uiModule)
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("miniproject.realm")
            .schemaVersion(1L)
            .migration(MigrationVersion())
            .build()
        Realm.setDefaultConfiguration(config)

        startKoin(this, appModules)
    }

    override fun onTerminate() {
        Realm.getDefaultInstance().close()
        super.onTerminate()
    }
}
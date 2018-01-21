package com.kamyshev.alexandr.presentation

import android.app.Application
import com.kamyshev.alexandr.data.repositories.repository.RepositoryProvider
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by alexandr on 20.01.18.
 */
class MyTodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this);
        val config: RealmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config);
    }
}
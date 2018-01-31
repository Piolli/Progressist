package com.kamyshev.alexandr.presentation

import android.app.Application
import android.graphics.Color
import com.kamyshev.alexandr.data.repositories.mappers.ProjectDbModelMapper
import com.kamyshev.alexandr.data.repositories.mappers.ProjectMapper
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.delete
import java.util.*

/**
 * Created by alexandr on 20.01.18.
 */
class MyTodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config: RealmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.delete(ProjectDbModel::class.java)
                val project1 = Project("Develop progressist project app", 2, arrayListOf(), UUID.randomUUID().toString(), Color.parseColor("#71CAFF"))
                val project2 = Project("Designer my application in sketch", 3, arrayListOf(), UUID.randomUUID().toString(), Color.parseColor("#55ba67"))
                val project3 = Project("Super project", 10, arrayListOf(), UUID.randomUUID().toString(), Color.DKGRAY)
                val project4 = Project("More profit for my debit card sberb", 2, arrayListOf(), UUID.randomUUID().toString(), Color.parseColor("#ba5f55"))
                it.insert(arrayListOf(ProjectMapper.map(project1), ProjectMapper.map(project2), ProjectMapper.map(project3), ProjectMapper.map(project4)))
            }
        }

        Realm.setDefaultConfiguration(config)
    }

}
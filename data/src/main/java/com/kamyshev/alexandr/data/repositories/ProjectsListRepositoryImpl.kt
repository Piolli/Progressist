package com.kamyshev.alexandr.data.repositories

import android.util.Log
import com.kamyshev.alexandr.data.repositories.mappers.ProjectDbModelMapper
import com.kamyshev.alexandr.data.repositories.mappers.ProjectMapper
import com.kamyshev.alexandr.domain.global.models.Project
import com.kamyshev.alexandr.domain.global.models.ProjectDbModel
import com.kamyshev.alexandr.domain.global.repositories.ProjectsListRepository
import io.realm.Realm

/**
 * Created by alexandr on 19.01.18.
 */
class ProjectsListRepositoryImpl() : ProjectsListRepository {
    val LOG = this.javaClass.simpleName

    override fun getProjects(): List<Project> {

        val projects = Realm.getDefaultInstance().where(ProjectDbModel::class.java).findAll()
        val listProjects = arrayListOf<Project>()
        projects.forEach { listProjects.add(it.toProject()) }
        Log.d(LOG, "Get projects: $projects")
        return listProjects
    }

    override fun getProjectByID(id: Int): Project {
        return Realm.getDefaultInstance()
                .where(ProjectDbModel::class.java)
                .equalTo("id", id)
                .findFirst()?.toProject() ?: Project("", 0, arrayListOf(), "", 0)
    }

    override fun saveProject(project: Project) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.insertOrUpdate(ProjectMapper.map(project))
                Log.d(LOG, "Save project: $project")
            }
        }
    }
}
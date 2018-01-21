package com.kamyshev.alexandr.domain.global.models

import com.kamyshev.alexandr.data.repositories.mappers.SubTaskDbModelMapper
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject

/**
 * Created by alexandr on 19.01.18.
 */

open class TaskDbModel(
        var name: String = "",
        var progress: Int = 0,
        var subTasks: RealmList<SubTaskDbModel> = RealmList()
): RealmObject() {
    fun toTask(): Task {
        return Task(name, progress, SubTaskDbModelMapper.map(subTasks))
    }
    /**
     * If complete tasks = all task,
     * then this task is complete
     */
    val isComplete
        get() = progress == subTasks.size
}
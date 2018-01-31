package com.kamyshev.alexandr.domain.global.models


import com.kamyshev.alexandr.data.repositories.mappers.TaskDbModelMapper
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by alexandr on 19.01.18.
 */
open class ProjectDbModel(
        var name: String = "",
        var progress: Int = 0,
        var tasks: RealmList<TaskDbModel> = RealmList(),
        var bgColor: Int = 0
): RealmObject() {

    @PrimaryKey var key: String = UUID.randomUUID().toString()

    fun toProject(): Project {
        return Project(name, progress, TaskDbModelMapper.map(tasks), key, bgColor)
    }

    /**
     * If completed tasks = all task,
     * then this task is complete
     */
    val isComplete
        get() = progress == tasks.size
}
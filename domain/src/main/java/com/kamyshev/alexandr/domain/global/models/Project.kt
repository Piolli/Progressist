package com.kamyshev.alexandr.domain.global.models

/**
 * Created by alexandr on 19.01.18.
 */
class Project(
        val name: String,
        val progress: Int,
        val tasks: List<Task>,
        val key: String,
        val bgColor: Int
) {
    /**
     * If completed tasks = all task,
     * then this task is complete
     */
    val isComplete
        get() = progress == tasks.size

    override fun toString(): String {
        return "Project(name='$name', progress=$progress, tasks=$tasks, key='$key')"
    }


}
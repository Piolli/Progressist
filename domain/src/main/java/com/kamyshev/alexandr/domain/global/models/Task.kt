package com.kamyshev.alexandr.domain.global.models

/**
 * Created by alexandr on 19.01.18.
 */
class Task (
        val name: String,
        val progress: Int,
        val subTasks: List<SubTask>
) {
    /**
     * If complete tasks = all task,
     * then this task is complete
     */
    val isComplete
        get() = progress == subTasks.size

    override fun toString(): String {
        return "Task(name='$name', progress=$progress, subTasks=$subTasks)"
    }

}
package com.kamyshev.alexandr.domain.global.models

/**
 * Created by alexandr on 19.01.18.
 */
class Task (
        val name: String,
        val progress: Int,
        var subTasks: List<SubTask>
) {
    /**
     * If complete tasks = all task,
     * then this task is complete
     */
    val isComplete
        get() =
            if(subTasks.isNotEmpty())
                progressCount == subTasks.size
            else false

    /**
     * Count sub tasks which is completed
     */
    val progressCount: Int
        get() = subTasks.count { it.isComplete }


    override fun toString(): String {
        return "Task(name='$name', progress=$progress, subTasks=$subTasks)"
    }

}
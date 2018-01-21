package com.kamyshev.alexandr.domain.global.models

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 * Created by alexandr on 19.01.18.
 */

open class SubTaskDbModel(
        var name: String = "",
        var isComplete: Boolean = false
): RealmObject() {

    fun toSubTask(): SubTask {
        return SubTask(name, isComplete)
    }
}
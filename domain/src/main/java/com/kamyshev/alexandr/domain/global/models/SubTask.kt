package com.kamyshev.alexandr.domain.global.models


/**
 * Created by alexandr on 19.01.18.
 */
class SubTask (
        val name: String,
        var isComplete: Boolean


) {
    override fun toString(): String {
        return "SubTask(name='$name', isComplete=$isComplete)"
    }
}
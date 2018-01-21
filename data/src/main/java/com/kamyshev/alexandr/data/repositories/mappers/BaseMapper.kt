package com.kamyshev.alexandr.data.repositories.mappers

/**
 * Created by alexandr on 19.01.18.
 */
/**
 * Convert [F] type to [T] type
 */
interface BaseMapper<F, T> {
    fun map(from: F): T
}
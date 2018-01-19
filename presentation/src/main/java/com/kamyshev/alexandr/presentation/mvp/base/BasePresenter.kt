package com.kamyshev.alexandr.presentation.mvp.base

/**
 * Created by alexandr on 19.01.18.
 */

interface BasePresenter<T> {
    var view: T

    fun BasePresenter(view: T) {
        this.view = view
    }

    fun onPause() { }

    fun onResume() { }

    fun onStop() { }

    fun onDestroy() { }
}
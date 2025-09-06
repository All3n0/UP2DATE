package io.eldohub.core.ui.base

import timber.log.Timber

object Up2DateLogger {
    private const val TAG = "UP2DATELOGGER"

    fun d(message: String) {
        Timber.d("$TAG $message")
    }

    fun e(message: String, throwable: Throwable? = null) {
        Timber.e(throwable, message)
    }

    fun i(message: String) {
        Timber.i("$TAG $message")
    }

    fun v(message: String) {
        Timber.v("$TAG $message")
    }

    fun w(message: String) {
        Timber.w("$TAG $message")
    }
}
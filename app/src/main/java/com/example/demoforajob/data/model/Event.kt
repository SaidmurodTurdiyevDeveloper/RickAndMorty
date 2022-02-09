package com.example.demoforajob.data.model

import com.example.demoforajob.zzz_utills.sendOneParametreBlock


open class Event<T>(private val content: T, val block: sendOneParametreBlock<T>? = null) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

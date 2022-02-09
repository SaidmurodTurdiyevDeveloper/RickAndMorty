package com.example.demoforajob.zzz_utills

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demoforajob.data.model.Event

fun Fragment.putArguments(block: Bundle.() -> Unit): Fragment {
    val bundle = arguments ?: Bundle()
    block(bundle)
    arguments = bundle
    return this
}

fun <T> Fragment.loadOnlyOneTimeObserver(data: Event<T>, block: T.() -> Unit) {
    val d = data.getContentIfNotHandled()
    if (d != null) {
        block.invoke(d)
    }
}
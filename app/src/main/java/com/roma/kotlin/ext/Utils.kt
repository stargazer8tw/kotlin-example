/** This is extension functions */
@file:JvmName("Utils")
package com.roma.kotlin.ext

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

/**
 * see https://qiita.com/yuichi_araki/items/f9b0778f927bccdf08ca
 */
fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}
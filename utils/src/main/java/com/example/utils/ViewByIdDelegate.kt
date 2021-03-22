package com.example.utils

import android.app.Activity
import android.app.Fragment
import android.view.View
import androidx.annotation.IdRes
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class ViewByIdDelegate<out T: View>(private val rootGetter: () -> View?, private val viewId: Int) {
    private var rootRef: WeakReference<View>? = null
    private var viewRef: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var view = viewRef
        var cachedRoot = rootRef?.get()
        var currrentRoot = rootGetter()
        if (currrentRoot != cachedRoot || view == null) {
            if (currrentRoot == null)
                if (view != null)
                    return view
                else
                    throw IllegalStateException("Cannot get View, no root")

            view = currrentRoot.findViewById(viewId)
            viewRef = view
            rootRef = WeakReference(currrentRoot)
        }

        checkNotNull(view) {
            "View not found"
        }

        return view
    }
}

fun <T: View> Activity.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({
        window.decorView.findViewById(android.R.id.content)
    }, viewId)
}

fun <T: View> Fragment.viewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({view}, viewId)
}
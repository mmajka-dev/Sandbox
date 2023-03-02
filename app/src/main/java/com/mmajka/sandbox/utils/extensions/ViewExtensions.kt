package com.mmajka.sandbox.utils.extensions

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

inline fun <T : View> T.setSafeOnClickListener(crossinline onClick: T.() -> Unit) {
    doOnAttach {
        setOnClickListener {
            onClick()
        }
    }
    doOnDetach {
        setOnClickListener(null)
    }
}

fun ImageView.load(
    data: Any?,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes fallback: Int = 0,
    @DrawableRes error: Int = fallback,
    useOriginalSize: Boolean = false,
) {
    Glide.with(this)
        .load(data)
        .placeholder(placeholder)
        .fallback(fallback)
        .error(error)
        .apply {
            if (useOriginalSize) {
                override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            }
        }
        .into(this)
}
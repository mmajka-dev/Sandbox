package com.mmajka.sandbox.model

import androidx.annotation.StringRes
import com.mmajka.sandbox.R

sealed class Feature(@StringRes val title: Int) {
    object PickImage : Feature(R.string.feature_pick_image)
}

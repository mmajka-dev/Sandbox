package com.mmajka.sandbox.presentation.pickImage

import java.io.File

data class PickImageViewState(
    val selectedImage: File? = null,
    val isLoading: Boolean = false
) {
    val isImageVisible = !isLoading
}

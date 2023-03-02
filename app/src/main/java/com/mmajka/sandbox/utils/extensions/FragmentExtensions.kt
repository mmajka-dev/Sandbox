package com.mmajka.sandbox.utils.extensions

import android.net.Uri
import androidx.fragment.app.Fragment
import com.mmajka.sandbox.presentation.pickImage.PickImageFragment
import java.io.File

fun Fragment.createFileFromUri(uri: Uri, filePrefix: String, fileExtension: String): File {
    val inputStream = requireActivity().contentResolver.openInputStream(uri)
    val file = File.createTempFile(
        filePrefix,
        fileExtension,
        requireContext().cacheDir,
    )

    inputStream.use { input ->
        file.outputStream().use { output ->
            input?.copyTo(output)
        }
    }
    return file
}
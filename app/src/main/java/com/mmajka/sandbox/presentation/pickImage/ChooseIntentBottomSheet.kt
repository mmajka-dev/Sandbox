package com.mmajka.sandbox.presentation.pickImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmajka.sandbox.BuildConfig
import com.mmajka.sandbox.databinding.BottomSheetChooseIntentBinding
import com.mmajka.sandbox.utils.contracts.TakePictureContract
import com.mmajka.sandbox.utils.extensions.createFileFromUri
import com.mmajka.sandbox.utils.extensions.setSafeOnClickListener
import java.io.File
import java.util.Date

class ChooseIntentBottomSheet(
    onReceiveFile: (File) -> Unit,
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetChooseIntentBinding? = null
    private val binding: BottomSheetChooseIntentBinding
        get() = _binding!!

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val file = createFileFromUri(
                    uri,
                    FILE_PREFIX,
                    FILE_EXTENSION,
                )
                onReceiveFile(file)
                dismiss()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(TakePictureContract()) { (isSuccess, uri) ->
            if (isSuccess) {
                val file = createFileFromUri(
                    uri,
                    FILE_PREFIX,
                    FILE_EXTENSION,
                )
                onReceiveFile(file)
                dismiss()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetChooseIntentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        galleryButton.setSafeOnClickListener {
            launchImagePicker()
        }

        takePictureButton.setSafeOnClickListener {
            launchCamera()
        }
    }

    private fun launchImagePicker() {
        imageLauncher.launch(PickVisualMediaRequest(ImageOnly))
    }

    private fun launchCamera() {
        val tempFile = File.createTempFile(
            Date().time.toString(),
            FILE_EXTENSION,
            requireContext().cacheDir,
        ).apply {
            createNewFile()
            deleteOnExit()
        }

        val uri = FileProvider.getUriForFile(
            requireContext(),
            BuildConfig.APPLICATION_ID + ".provider",
            tempFile,
        )
        cameraLauncher.launch(uri)
    }

    companion object {
        private const val FILE_PREFIX = "temp_img_"
        private const val FILE_EXTENSION = ".png"
    }
}

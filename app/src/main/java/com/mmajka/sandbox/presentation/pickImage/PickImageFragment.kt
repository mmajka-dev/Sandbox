package com.mmajka.sandbox.presentation.pickImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mmajka.sandbox.databinding.FragmentPickImageBinding
import com.mmajka.sandbox.utils.Constants.NO_ITEM_SELECTED
import com.mmajka.sandbox.utils.extensions.createFileFromUri
import com.mmajka.sandbox.utils.extensions.load
import com.mmajka.sandbox.utils.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  @note Picked image is saving to file for later send to BE
 **/

@AndroidEntryPoint
class PickImageFragment : Fragment() {

    private val viewModel by viewModels<PickImageViewModel>()
    private var _binding: FragmentPickImageBinding? = null
    private val binding get() = _binding!!

    private val imageLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            val file = createFileFromUri(uri, FILE_PREFIX, FILE_EXTENSION)
            viewModel.onImageSelected(file)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPickImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        lifecycleScope.launch {
            viewModel.event.collectLatest { event ->
                onNewEvent(event)
            }
        }
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                onNewState(state)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViews() = with(binding) {
        pickImageButton.setSafeOnClickListener {
            viewModel.onPickImageButtonClicked()
        }
        backButton.setSafeOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onNewEvent(event: PickImageViewEvent) {
        when (event) {
            PickImageViewEvent.OnPickImageButtonClicked -> launchImagePicker()
            is PickImageViewEvent.ShowMessage -> showSnackbar(event.message)
        }
    }

    private fun onNewState(state: PickImageViewState) {
        binding.selectedImageTextView.text = buildString {
            append("Path: ", state.selectedImage?.path ?: NO_ITEM_SELECTED)
        }
        binding.photoImageView.isVisible = state.isImageVisible
        binding.photoImageView.load(state.selectedImage)
        binding.progressIndicator.isVisible = state.isLoading
    }

    private fun launchImagePicker() {
        imageLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val FILE_PREFIX = "temp_img_"
        private const val FILE_EXTENSION = ".png"
    }
}

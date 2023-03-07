package com.mmajka.sandbox.presentation.pickImage

import androidx.lifecycle.ViewModel
import com.mmajka.sandbox.presentation.pickImage.PickImageViewEvent.OnPickImageButtonClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PickImageViewModel @Inject constructor() : ViewModel() {

    private val mutableState = MutableStateFlow(PickImageViewState())
    val state: StateFlow<PickImageViewState> = mutableState.asStateFlow()

    private val mutableEvent = Channel<PickImageViewEvent>(Channel.BUFFERED)
    val event: Flow<PickImageViewEvent> = mutableEvent.receiveAsFlow()

    fun onPickImageButtonClicked() {
        mutableEvent.trySend(OnPickImageButtonClicked)
    }

    fun onImageSelected(file: File) {
        mutableState.value = mutableState.value.copy(isLoading = true)
        mutableState.value = mutableState.value.copy(
            selectedImage = file,
        )
        mutableState.value = mutableState.value.copy(isLoading = false)
    }
}

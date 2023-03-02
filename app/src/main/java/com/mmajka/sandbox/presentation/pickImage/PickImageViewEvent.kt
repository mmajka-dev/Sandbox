package com.mmajka.sandbox.presentation.pickImage

sealed interface PickImageViewEvent {
    object OnPickImageButtonClicked : PickImageViewEvent
    data class ShowMessage(val message: String) : PickImageViewEvent
}

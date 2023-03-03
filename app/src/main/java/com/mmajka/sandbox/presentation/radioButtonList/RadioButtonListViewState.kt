package com.mmajka.sandbox.presentation.radioButtonList

import com.mmajka.sandbox.model.RadioButtonItem

data class RadioButtonListViewState(
    val radioButtons: List<RadioButtonItem> = emptyList(),
    val selectedItem: RadioButtonItem? = null
)

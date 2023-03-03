package com.mmajka.sandbox.presentation.radioButtonList

import androidx.lifecycle.ViewModel
import com.mmajka.sandbox.model.RadioButtonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RadioButtonListViewModel @Inject constructor() : ViewModel() {

    private val mutableState = MutableStateFlow(RadioButtonListViewState())
    val state: StateFlow<RadioButtonListViewState> = mutableState.asStateFlow()

    init {
        mutableState.value = mutableState.value.copy(
            radioButtons = getRadioButtonList(),
        )
    }

    fun onItemSelected(position: Int) {
        val selectedItem = state.value.radioButtons[position]

        mutableState.value = mutableState.value.copy(
            selectedItem = selectedItem,
        )
    }

    private fun getRadioButtonList(): List<RadioButtonItem> {
        val list = buildList {
            for (i in 0 until 10) {
                add(
                    RadioButtonItem(
                        id = i,
                        text = "Radio Button no. $i",
                    ),
                )
            }
        }
        return list
    }
}

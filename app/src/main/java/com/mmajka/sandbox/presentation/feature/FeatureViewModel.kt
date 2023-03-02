package com.mmajka.sandbox.presentation.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmajka.sandbox.domain.GetFeatureListUseCase
import com.mmajka.sandbox.presentation.feature.FeatureViewEvent.OnFeatureSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val getFeatureListUseCase: GetFeatureListUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(FeatureViewState())
    val state: StateFlow<FeatureViewState> = mutableState.asStateFlow()

    private val mutableEvent = Channel<FeatureViewEvent>(Channel.BUFFERED)
    val event: Flow<FeatureViewEvent> = mutableEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            mutableState.value = mutableState.value.copy(isLoading = true)
            mutableState.value = mutableState.value.copy(
                featureList = getFeatureListUseCase(),
            )
            mutableState.value = mutableState.value.copy(isLoading = false)
        }
    }

    fun onFeatureClick(position: Int) {
        val feature = state.value.featureList[position]
        mutableEvent.trySend(OnFeatureSelected(feature))
    }
}

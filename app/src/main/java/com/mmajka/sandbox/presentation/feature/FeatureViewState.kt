package com.mmajka.sandbox.presentation.feature

import com.mmajka.sandbox.model.Feature

data class FeatureViewState(
    val featureList: List<Feature> = emptyList(),
    val isLoading: Boolean = false
)

package com.mmajka.sandbox.presentation.feature

import com.mmajka.sandbox.model.Feature

sealed interface FeatureViewEvent {
    data class OnFeatureSelected(val feature: Feature) : FeatureViewEvent
}
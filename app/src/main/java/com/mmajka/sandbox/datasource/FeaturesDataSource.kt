package com.mmajka.sandbox.datasource

import com.mmajka.sandbox.model.Feature
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeaturesDataSource @Inject constructor() {

    suspend fun getFeatureList(): List<Feature> {
        delay(500)
        return listOf(
            Feature.PickImage,
        )
    }
}

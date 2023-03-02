package com.mmajka.sandbox.domain

import com.mmajka.sandbox.datasource.FeaturesDataSource
import javax.inject.Inject

class GetFeatureListUseCase @Inject constructor(
    private val featuresDataSource: FeaturesDataSource
) {
    suspend operator fun invoke() = featuresDataSource.getFeatureList()
}
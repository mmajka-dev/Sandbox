package com.mmajka.sandbox.presentation.feature.ui

import androidx.recyclerview.widget.RecyclerView
import com.mmajka.sandbox.databinding.ItemFeatureBinding
import com.mmajka.sandbox.model.Feature
import com.mmajka.sandbox.utils.OnListItemClick
import com.mmajka.sandbox.utils.extensions.setSafeOnClickListener

class FeatureViewHolder(
    private val binding: ItemFeatureBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Feature) = with(binding) {
        binding.featureTitleTextView.text = featureTitleTextView.context.getString(item.title)
    }

    fun setOnClickListener(onClick: OnListItemClick) {
        binding.root.setSafeOnClickListener {
            onClick(absoluteAdapterPosition)
        }
    }
}

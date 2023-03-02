package com.mmajka.sandbox.presentation.feature.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.sandbox.databinding.ItemFeatureBinding
import com.mmajka.sandbox.model.Feature
import com.mmajka.sandbox.utils.OnListItemClick

class FeatureAdapter(
    onFeatureClickListener: OnListItemClick,
) : ListAdapter<Feature, FeatureViewHolder>(diffUtil) {

    private var onFeatureClick: OnListItemClick? = onFeatureClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeatureViewHolder(
        ItemFeatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.setOnClickListener {
            onFeatureClick?.invoke(it)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onFeatureClick = null
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Feature>() {
            override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean =
                oldItem.title == newItem.title
        }
    }
}

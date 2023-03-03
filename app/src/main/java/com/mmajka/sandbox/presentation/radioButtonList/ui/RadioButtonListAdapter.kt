package com.mmajka.sandbox.presentation.radioButtonList.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.sandbox.databinding.ItemRadioButtonBinding
import com.mmajka.sandbox.model.RadioButtonItem
import com.mmajka.sandbox.utils.OnListItemClick

class RadioButtonListAdapter(
    onClickListener: OnListItemClick,
) : ListAdapter<RadioButtonItem, RadioButtonViewHolder>(diffUtil) {

    private var onClick: OnListItemClick? = onClickListener

    private var selectedItem: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RadioButtonViewHolder(
        ItemRadioButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    )

    override fun onBindViewHolder(holder: RadioButtonViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.setOnClickListener {
            if (selectedItem == -1) {
                selectedItem = position
                holder.bind(currentList[position].copy(isSelected = true))
            } else {
                if (selectedItem != position) {
                    holder.bind(currentList[selectedItem].copy(isSelected = false))
                    selectedItem = position
                } else {
                    holder.bind(currentList[position].copy(isSelected = true))
                }
            }

            onClick?.invoke(it)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onClick = null
    }

    private fun onItemSelected(position: Int) {
        // todo
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RadioButtonItem>() {
            override fun areItemsTheSame(
                oldItem: RadioButtonItem,
                newItem: RadioButtonItem,
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: RadioButtonItem,
                newItem: RadioButtonItem,
            ): Boolean = oldItem.id == newItem.id
        }
    }
}

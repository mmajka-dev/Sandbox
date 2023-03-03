package com.mmajka.sandbox.presentation.radioButtonList.ui

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.mmajka.sandbox.databinding.ItemRadioButtonBinding
import com.mmajka.sandbox.model.RadioButtonItem
import com.mmajka.sandbox.utils.OnListItemClick
import com.mmajka.sandbox.utils.extensions.setSafeOnClickListener

class RadioButtonViewHolder(
    private val binding: ItemRadioButtonBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RadioButtonItem) {
        Log.e("IS_CHECKED", item.isSelected.toString() + " " + absoluteAdapterPosition)
        binding.root.text = item.text
        binding.root.isChecked = item.isSelected
    }

    fun setOnClickListener(onClick: OnListItemClick) {
        binding.root.setSafeOnClickListener {
            onClick(absoluteAdapterPosition)
        }
    }
}

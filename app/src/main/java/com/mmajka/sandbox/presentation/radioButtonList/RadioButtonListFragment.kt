package com.mmajka.sandbox.presentation.radioButtonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mmajka.sandbox.databinding.FragmentRadioButtonListBinding
import com.mmajka.sandbox.presentation.radioButtonList.ui.RadioButtonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RadioButtonListFragment : Fragment() {

    private val viewModel by viewModels<RadioButtonListViewModel>()
    private var _binding: FragmentRadioButtonListBinding? = null
    private val binding
        get() = _binding!!

    private var adapter: RadioButtonListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRadioButtonListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                onNewState(state)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

    private fun initViews() = with(binding) {
        adapter = RadioButtonListAdapter { position ->
            viewModel.onItemSelected(position)
        }
        radioButtonRecyclerView.adapter = adapter
    }

    private fun onNewState(state: RadioButtonListViewState) {
        adapter?.submitList(state.radioButtons)
    }
}

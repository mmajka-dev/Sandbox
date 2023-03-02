package com.mmajka.sandbox.presentation.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mmajka.sandbox.databinding.FragmentChooseFeatureBinding
import com.mmajka.sandbox.feature.ChooseFeatureFragmentDirections
import com.mmajka.sandbox.presentation.feature.ui.FeatureAdapter
import com.mmajka.sandbox.model.Feature
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseFeatureFragment : Fragment() {

    private val viewModel by viewModels<FeatureViewModel>()
    private var adapter: FeatureAdapter? = null

    private var _binding: FragmentChooseFeatureBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseFeatureBinding.inflate(layoutInflater)
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
        lifecycleScope.launch {
            viewModel.event.collectLatest { event ->
                onNewEvent(event)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViews() = with(binding) {
        adapter = FeatureAdapter { position ->
            viewModel.onFeatureClick(position)
        }
        featuresRecyclerView.adapter = adapter
    }

    private fun onNewState(state: FeatureViewState) {
        adapter?.submitList(state.featureList)
    }

    private fun onNewEvent(event: FeatureViewEvent) {
        when (event) {
            is FeatureViewEvent.OnFeatureSelected -> navigateToFeature(event.feature)
        }
    }

    private fun navigateToFeature(feature: Feature) {
        when (feature) {
            Feature.PickImage -> findNavController().navigate(
                ChooseFeatureFragmentDirections.navigatePickImage(),
            )
        }
    }
}

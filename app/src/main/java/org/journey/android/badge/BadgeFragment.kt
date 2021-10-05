package org.journey.android.badge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.journey.android.databinding.FragmentBadgeBinding
import org.journey.android.util.AutoClearedValue

@AndroidEntryPoint
class BadgeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentBadgeBinding>()
    private val viewModel by viewModels<BadgeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBadgeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        loadBadgeList()
        clickAssetListener()
    }
    private fun loadBadgeList(){
        binding.recyclerviewObtainedBadge.apply {
            this.adapter = BadgeAdapter()
            viewModel.badgeList.observe(viewLifecycleOwner){
                (adapter as BadgeAdapter).badgeList = it.toMutableList()
            }
        }
    }

    private fun clickAssetListener(){
        with(binding){
            buttonBack.setOnClickListener { findNavController().popBackStack() }
        }
    }
}
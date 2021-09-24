package org.journey.android.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.journey.android.R
import org.journey.android.databinding.FragmentChallengeBinding

import org.journey.android.util.AutoClearedValue

@AndroidEntryPoint
class ChallengeFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentChallengeBinding>()
    private val viewModel by viewModels<ChallengeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setClickListener()
    }
    private fun setClickListener(){
        with(binding){
            imagebuttonChallengeBrowse.setOnClickListener { findNavController().navigate(R.id.action_frameFragment_to_courseCatalogFragment) }
        }
    }

}


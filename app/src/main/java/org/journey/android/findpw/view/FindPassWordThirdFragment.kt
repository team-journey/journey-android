package org.journey.android.findpw.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.journey.android.databinding.FragmentFindPasswordThirdBinding
import org.journey.android.util.AutoClearedValue

@AndroidEntryPoint
class FindPassWordThirdFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentFindPasswordThirdBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPasswordThirdBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
package org.journey.android.reward

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import org.journey.android.R
import org.journey.android.base.BaseFragment
import org.journey.android.databinding.FragmentRewardBinding

class RewardFragment : BaseFragment<FragmentRewardBinding>() {
    private lateinit var rewardAdapter: RewardAdapter
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRewardBinding {
      return FragmentRewardBinding.inflate(inflater, container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showReward()
    }
    private fun showReward(){
        rewardAdapter = RewardAdapter()
        binding.recyclerviewReward.adapter = rewardAdapter
        rewardAdapter.rewardList.addAll(
            listOf<RewardData>(
                RewardData(
                    percent = "09",
                    percentUnit = "%",
                    rewardContent = requireContext().getString(R.string.reward_challenge_percent)
                ),
                RewardData(
                    percent= "09",
                    percentUnit = "일",
                    rewardContent = requireContext().getString(R.string.reward_challenge_date)
                )
            )
        )
        rewardAdapter.notifyDataSetChanged()
    }
}
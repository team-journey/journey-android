package org.journey.android.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import org.journey.android.R
import org.journey.android.base.BaseFragment
import org.journey.android.databinding.FragmentOnboardingFirstBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentOnboardingFirstBinding>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val viewModel by viewModels<LoginViewModel>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingFirstBinding {
        return FragmentOnboardingFirstBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener(){
        with(binding){
            textviewEmailAccount.setOnClickListener { Navigation.findNavController(binding.root).navigate(
                R.id.action_onboardingFirstFragment_to_emailLoginFragment
            ) }
            buttonLoginEmail.setOnClickListener { Navigation.findNavController(binding.root).navigate(
                R.id.action_onboardingFirstFragment_to_emailSignupFragment
            ) }
        }
    }

}
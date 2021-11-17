package org.journey.android.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.journey.android.base.DisposableViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : DisposableViewModel(){
    val wholePolicyAllowed = MutableLiveData<Boolean>()
    val serviceAllowed = MutableLiveData<Boolean>()
    val personalInformationAllowed = MutableLiveData<Boolean>()
    val serviceAgreementList = listOf(serviceAllowed, personalInformationAllowed)

    fun checkEveryServiceAgreement(){
        wholePolicyAllowed.value = serviceAgreementList.all { it.value  == true}
    }

    fun changeEveryPolicyAllowed() {
        wholePolicyAllowed.value.run {
            personalInformationAllowed.value = this
            serviceAllowed.value = this
        }
    }
}
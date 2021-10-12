package com.tree.chope.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) : ViewModel (){

    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun checkIsLoggedIn(){
        if( preferenceHelper.read(PreferenceKey.USER.name, "").isNullOrEmpty()){
            isLoggedIn.postValue(false)
        }else{
            isLoggedIn.postValue(true)
        }
    }

}
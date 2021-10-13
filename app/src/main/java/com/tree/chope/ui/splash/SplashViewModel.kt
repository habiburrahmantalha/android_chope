package com.tree.chope.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.PreferenceHelper
import com.tree.chope.backend.data.ChatHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) : ViewModel (){

    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    private fun checkIsLoggedIn(){
        if( preferenceHelper.read(PreferenceKey.USER.name, "").isNullOrEmpty()){
            isLoggedIn.postValue(false)
        }else{
            isLoggedIn.postValue(true)
        }
    }

    fun prepareChatHistory() {

        val list : List<ChatHistory> =  preferenceHelper.getChatHistory();
        val chatHistory: MutableList<ChatHistory> = mutableListOf()

        if(list.isEmpty()) {
            chatHistory.add(ChatHistory(id = 1, userId = 2, name = "Jone", createdAt = "2021-10-13 12:45:36", lastMessage = "How are you?"))
            chatHistory.add(ChatHistory(id = 2, userId = 3, name = "Kent", createdAt = "2021-10-13 6:39:57", lastMessage = "Hello"))
            preferenceHelper.addChatHistory(chatHistory);
        }

        checkIsLoggedIn();
    }

}
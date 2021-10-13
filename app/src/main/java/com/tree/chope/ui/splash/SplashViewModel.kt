package com.tree.chope.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.PreferenceHelper
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.backend.data.Message
import com.tree.chope.formatTo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
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
            val c1: ChatHistory = ChatHistory(id = 1, userId = 2, name = "Jone", createdAt = "2021-10-13 12:45:36", lastMessage = "How are you?")
            chatHistory.add(c1)
            addConversation(c1)
            val c2: ChatHistory = ChatHistory(id = 2, userId = 3, name = "Kent", createdAt = "2021-10-13 6:39:57", lastMessage = "Hello")
            chatHistory.add(c2)
            addConversation(c2)
            preferenceHelper.addChatHistory(chatHistory);
        }

        checkIsLoggedIn();
    }

    private fun addConversation(c: ChatHistory) {
        preferenceHelper.addConversation(c.id,  Message(userId = 1, message = c.lastMessage, createdAt = c.createdAt, name = "Me"))
        preferenceHelper.addConversation(c.id,  Message(userId = c.userId, message = c.lastMessage, createdAt = c.createdAt, name = c.name))
    }

}
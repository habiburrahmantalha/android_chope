package com.tree.chope.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.PreferenceHelper
import com.tree.chope.backend.data.ChatHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) : ViewModel (){


    val chatHistoryLiveData: MutableLiveData<List<ChatHistory?>> = MutableLiveData()
    val chatLiveData: MutableLiveData<ChatHistory> = MutableLiveData()

    fun prepareChatHistory(){
        val list : List<ChatHistory> =  preferenceHelper.getChatHistory();
        chatHistoryLiveData.postValue(list);
    }

    fun onChatItemClicked(chat: ChatHistory) {
        chatLiveData.postValue(chat)
    }
}
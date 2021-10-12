package com.tree.chope.ui.chat

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.PreferenceHelper
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.backend.data.Message
import com.tree.chope.formatTo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) : ViewModel (){


    val conversationLiveData: MutableLiveData<List<Message?>> = MutableLiveData()

    val conversationList: MutableList<Message?> = mutableListOf()
    private var chatHistory: ChatHistory? = null

    fun getConversation(){

        conversationList.addAll(preferenceHelper.getConversation(chatHistory?.id))
        conversationLiveData.postValue(conversationList)

    }

    fun sendText(text: String?) {
        val m = Message(id = conversationList.size +1, userId = 1, message = text, createdAt = Calendar.getInstance().time.formatTo());
        conversationList.add(m)
        preferenceHelper.addConversation(chatHistory?.id, m);
        val m2 = Message(id = conversationList.size +1, userId = chatHistory?.userId, message = text, createdAt = Calendar.getInstance().time.formatTo())
        conversationList.add(m2)
        preferenceHelper.addConversation(chatHistory?.id, m);
        conversationLiveData.postValue(conversationList)
    }

    fun setChatHistory(c: ChatHistory) {
        chatHistory = c
    }
}
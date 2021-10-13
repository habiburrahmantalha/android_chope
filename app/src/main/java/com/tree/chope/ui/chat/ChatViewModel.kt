package com.tree.chope.ui.chat

import android.os.Looper
import android.text.Editable
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val preferenceHelper: PreferenceHelper) : ViewModel (){


    val conversationLiveData: MutableLiveData<List<Message?>> = MutableLiveData()

    val conversationList: MutableList<Message?> = mutableListOf()
    private var chatHistory: ChatHistory? = null

    fun getConversation(){
        conversationList.addAll(preferenceHelper.getConversation(chatHistory?.id))
        Log.d("Conversation",conversationList.toString() )
        conversationLiveData.postValue(conversationList)
    }

    fun sendText(text: String?) {
        if(text.isNullOrBlank())
            return
        val m = Message(userId = 1, message = text, createdAt = Calendar.getInstance().time.formatTo(dateFormat = "yyyy-MM-dd HH:mm:ss", timeZone = TimeZone.getTimeZone("UTC")), name = "Me");
        conversationList.add(m)
        preferenceHelper.addConversation(chatHistory?.id, m)
        conversationLiveData.postValue(conversationList)


        GlobalScope.launch(context = Dispatchers.Main) {
            delay(500)
            val m2 = Message(userId = chatHistory?.userId, message = text, createdAt = Calendar.getInstance().time.formatTo(dateFormat = "yyyy-MM-dd HH:mm:ss", timeZone = TimeZone.getTimeZone("UTC"))
            , name = chatHistory?.name)
            conversationList.add(m2)
            preferenceHelper.addConversation(chatHistory?.id, m2);
            conversationLiveData.postValue(conversationList)
        }

    }

    fun setChatHistory(c: ChatHistory) {
        chatHistory = c
        getConversation()
    }
}
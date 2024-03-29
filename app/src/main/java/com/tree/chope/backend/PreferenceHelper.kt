package com.tree.chope.backend

import android.content.SharedPreferences
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tree.chope.PreferenceKey
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.backend.data.Message

class PreferenceHelper(private val prefs: SharedPreferences) {

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }
    fun read(key: String, value: Long): Long? {
        return prefs.getLong(key, value)
    }
    fun read(key: String, value: Boolean): Boolean? {
        return prefs.getBoolean(key, value)
    }
    fun write(key: String, value: String?) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }
    fun write(key: String, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean(key, value)
            commit()
        }
    }
    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }

    fun clear(key: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.remove(key).apply()
    }

    fun clear() {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.clear().apply()
    }

    fun getConversation(id: Int?): List<Message>{
        val s = read(PreferenceKey.CONVERSATION.name+"_$id","").takeIf { i-> !i.isNullOrEmpty()}.orEmpty()
        if(s.isNotEmpty()) {
            val listType = Types.newParameterizedType(List::class.java, Message::class.java)
            val adapter: JsonAdapter<MutableList<Message>> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(listType)
            val result = adapter.fromJson(s)
            result?.let { list ->
                return list
            }
        }
        return listOf()
    }

    fun addConversation(id: Int?,message: Message) {
        val s = read(PreferenceKey.CONVERSATION.name+"_$id","")
        if(s.isNullOrEmpty()){
            val list: MutableList<Message> = mutableListOf()
            message.id = 1
            list.add(message)
            val listType = Types.newParameterizedType(List::class.java, Message::class.java)
            val adapter: JsonAdapter<List<Message>> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(listType)
            write(PreferenceKey.CONVERSATION.name+"_$id", adapter.toJson(list) )
        }else{
            val listType = Types.newParameterizedType(List::class.java, Message::class.java)
            val adapter: JsonAdapter<MutableList<Message>> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(listType)
            val result = adapter.fromJson(s)
            result?.let { list ->
                message.id = list.size
                list.add(message)
                write(PreferenceKey.CONVERSATION.name+"_$id", adapter.toJson(list) )
            }
        }
        if(message.userId != 1) {
            updateChatHistory(id, message)
        }
    }

    private fun updateChatHistory(id: Int?, message: Message) {

        val list: MutableList<ChatHistory> = getChatHistory().toMutableList()

        val i = list.indexOfFirst { it.id == id }
        if(i>=0){
            list[i] = ChatHistory(id = id, userId = message.userId, createdAt = message.createdAt, lastMessage =  message.message, name = message.name)
        }

        addChatHistory(list);
    }

    fun getChatHistory(): List<ChatHistory>{
        val s = read(PreferenceKey.HISTORY.name,"").takeIf { i-> !i.isNullOrEmpty()}.orEmpty()
        if(s.isNotBlank()) {
            val listType = Types.newParameterizedType(List::class.java, ChatHistory::class.java)
            val adapter: JsonAdapter<MutableList<ChatHistory>> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(listType)
            Log.d("History", s)
            val result = adapter.fromJson(s)
            result?.let { list ->
                return list
            }
        }
        return listOf()
    }

    fun addChatHistory(chatHistory: MutableList<ChatHistory>) {
        val listType = Types.newParameterizedType(List::class.java, ChatHistory::class.java)
        val adapter: JsonAdapter<MutableList<ChatHistory>> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(listType)
        write(PreferenceKey.HISTORY.name, adapter.toJson(chatHistory) )
    }

}

package com.tree.chope.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tree.chope.R
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.ui.chat.ChatActivity
import com.tree.chope.ui.splash.SplashActivity
import com.tree.chope.ui.splash.SplashViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {

        val rvChats = findViewById<RecyclerView>(R.id.rv_chats)

        if( rvChats.itemDecorationCount > 0){
            rvChats.removeItemDecorationAt(0)
        }

        rvChats.layoutManager = LinearLayoutManager(this)
        val adapter = ChatsAdapter(listOf(), viewModel)
        rvChats.adapter = adapter

        viewModel.chatLiveData.observe(this, Observer {
            navigateToChat(it)
        })
        viewModel.chatHistoryLiveData.observe(this, Observer {
            Log.d("Item", "list update ${it.size}")
            adapter.updateList(it)
            it.forEach { chat ->
                Log.d("Item", chat!!.name!!)
            }
        })

        viewModel.restartLiveData.observe(this, {

            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })
        val clearButton = findViewById<Button>(R.id.button_clear)
        clearButton.setOnClickListener {
            viewModel.clearData()
        }


    }
    private fun navigateToChat(chat: ChatHistory?) {
        chat?.let {
            ChatActivity.chatHistory = it
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.prepareChatHistory()
    }
}
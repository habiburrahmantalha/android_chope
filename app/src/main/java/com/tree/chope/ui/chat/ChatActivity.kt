package com.tree.chope.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tree.chope.R
import com.tree.chope.backend.data.ChatHistory
import com.tree.chope.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: ChatViewModel by viewModels()

    companion object {
        @JvmStatic lateinit var chatHistory: ChatHistory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator ( R.drawable.ic_back );
        }

        toolbar.setNavigationOnClickListener{
            onClick(findViewById(R.id.toolbar))
        }
        toolbar.title = chatHistory.name

        viewModel.setChatHistory(chatHistory)

        initView()

    }

    private fun initView() {

        val sendButton = findViewById<Button>(R.id.button_chat_send)
        val rvConversation = findViewById<RecyclerView>(R.id.rv_conversation)
        sendButton.setOnClickListener(this);

        if( rvConversation.itemDecorationCount > 0){
            rvConversation.removeItemDecorationAt(0)
        }

        rvConversation.layoutManager = LinearLayoutManager(this)
        val adapter = ChatAdapter(listOf(), viewModel as ChatViewModel)
        rvConversation.adapter = adapter

        viewModel.conversationLiveData.observe(this, Observer {
            adapter.updateList(it)
            rvConversation.scrollToPosition(it.size-1)
        })

        val et = findViewById<EditText>(R.id.edit_chat_message)
        et.setOnFocusChangeListener { _, focus ->
            rvConversation.scrollToPosition(adapter.itemCount - 1)
            Log.d("Focus Change",focus.toString() )
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toolbar -> {
                onBackPressed()
            }
            R.id.button_chat_send -> {
                val et = findViewById<EditText>(R.id.edit_chat_message)

                viewModel.sendText(et.text.toString());
                et.text.clear()
            }
        }
    }
}
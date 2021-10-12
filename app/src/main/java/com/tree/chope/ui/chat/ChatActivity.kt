package com.tree.chope.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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
        sendButton.setOnClickListener(this);
//        tv_category_name.text = category.name
//        if( rv_stories.itemDecorationCount > 0){
//            rv_stories.removeItemDecorationAt(0)
//        }
//        rv_stories.addItemDecoration(
//            ItemDecoration(
//                convertDpToPixel(8, this),
//                convertDpToPixel(16, this),
//                grid = true
//            )
//        )
//        rv_stories.layoutManager = GridLayoutManager(this, 2)
//        val adapter = StoriesAdapter(listOf(), viewModel.get() as StoriesVM)
//        rv_stories.adapter = adapter
//
//        setListeners()
//        viewModel.loadData(category.id!!)
//
//        viewModel.get().storyLiveData.observe(this, Observer {
//            navigateToStory(it)
//        })
//        viewModel.get().StoryListLiveData.observe(this, Observer {
//            adapter.updateList(it)
//        })

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toolbar -> {
                onBackPressed()
            }
            R.id.button_chat_send -> {

                val et = findViewById<EditText>(R.id.edit_chat_message)
                viewModel.sendText(et.text.toString());
                et.clearComposingText()
            }
        }
    }
}
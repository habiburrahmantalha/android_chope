package com.tree.chope.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import com.tree.chope.ui.home.MainActivity
import com.tree.chope.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        GlobalScope.launch(context = Dispatchers.Main) {
            delay(20000)
            viewModel.checkIsLoggedIn()
        }

        viewModel.isLoggedIn.observe(this, {
            when {
                it -> navigateToHome()
                //else -> navigateToStart()
            }
        })
    }

//    private fun navigateToStart() {
//        val intent = Intent(this, Activity::class.java)
//        startActivity(intent)
//        finish()
//    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
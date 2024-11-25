package com.ssafy.smartstore_jetpack.presentation.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen, ResourceAsColor")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
        }

        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<ImageView>(R.id.iv_splash_image)

        lifecycleScope.launch {
            viewModel.appThemeName.collectLatest { themeName ->
                when (themeName) {
                    "봄" -> {
                        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.spring_secondary)
                        Glide.with(this@SplashActivity).load(R.raw.splash_spring).centerCrop().into(splashImage)
                    }

                    "여름" -> {
                        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.summer_secondary)
                        Glide.with(this@SplashActivity).load(R.raw.splash_summer).centerCrop().into(splashImage)
                    }

                    "가을" -> {
                        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.autumn_secondary)
                        Glide.with(this@SplashActivity).load(R.raw.splash_autumn).centerCrop().into(splashImage)
                    }

                    "겨울" -> {
                        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.winter_secondary)
                        Glide.with(this@SplashActivity).load(R.raw.splash_winter).centerCrop().into(splashImage)
                    }

                    else -> {
                        window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.main_end)
                        Glide.with(this@SplashActivity).load(R.raw.splash_main).centerCrop().into(splashImage)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.splashUiEvent.collectLatest { uiEvent ->
                when (uiEvent) {
                    is SplashUiEvent.LoginSuccess -> {
                        delay(1300L)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                            putExtra("login_success", true)
                        })
                        finish()
                    }

                    is SplashUiEvent.LoginFailed -> {
                        delay(1300L)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                            putExtra("login_success", false)
                        })
                        finish()
                    }
                }
            }
        }
    }
}
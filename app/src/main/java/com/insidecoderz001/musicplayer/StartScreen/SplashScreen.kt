package com.insidecoderz001.musicplayer.StartScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.insidecoderz001.musicplayer.MainActivity
import com.insidecoderz001.musicplayer.R
import java.util.*

class SplashScreen : AppCompatActivity() {

    private  lateinit var splashScreen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        ids()

        val background = object :Thread(){
            override fun run() {
                try {
                    sleep(3000)
                    startActivity(Intent(this@SplashScreen,MainActivity::class.java))
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }

    private fun ids() {
        splashScreen =findViewById(R.id.splash)
    }
}
package com.insidecoderz001.musicplayer.Classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.insidecoderz001.musicplayer.PlayerActivity
import com.insidecoderz001.musicplayer.R
import kotlin.system.exitProcess

class NotificationReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREV ->prevNextSong(false,context!!)
            ApplicationClass.PLAY ->if (PlayerActivity.isPlaying) Pause() else playPause()
            ApplicationClass.NEXT ->prevNextSong(true,context!!)
            ApplicationClass.EXIT -> {
                PlayerActivity.musicService!!.stopForeground(true)
                PlayerActivity.musicService=null
                exitProcess(1)
            }
        }
    }

    private fun prevNextSong(inc:Boolean,context:Context){
        checksongPosition(inc)
//        PlayerActivity.musicService!!.mediaPlayer!!.setDataSource(PlayerActivity.musicPA[PlayerActivity.songPosition].path)
//        PlayerActivity.musicService!!.mediaPlayer!!.prepare()
//        PlayerActivity.binding.playPause.setImageResource(R.drawable.ic_baseline_pause_24)
//        PlayerActivity.musicService!!.showNotifiaction(R.drawable.ic_baseline_pause_24)

        createMediaPlayer()
        Glide.with(context)
            .load(PlayerActivity.musicPA[PlayerActivity.songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_icon).centerCrop())
            .into(PlayerActivity.binding.songImg)
        PlayerActivity.binding.songName.text= PlayerActivity.musicPA[PlayerActivity.songPosition].title
        playPause()
    }

    private fun playPause(){
        PlayerActivity.isPlaying =true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotifiaction(R.drawable.ic_baseline_pause_24)
        PlayerActivity.binding.playPause.setImageResource(R.drawable.ic_baseline_pause_24)

    }

    private fun Pause(){
        PlayerActivity.isPlaying =false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotifiaction(R.drawable.play)
        PlayerActivity.binding.playPause.setImageResource(R.drawable.play)

    }

}
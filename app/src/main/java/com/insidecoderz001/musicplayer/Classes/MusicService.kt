package com.insidecoderz001.musicplayer.Classes

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.insidecoderz001.musicplayer.PlayerActivity
import com.insidecoderz001.musicplayer.R

class MusicService: Service() {

    private var myBinder =MyBinder()
    var mediaPlayer :MediaPlayer?=null
    private lateinit var mediasession :MediaSessionCompat


    override fun onBind(p0: Intent?): IBinder {
        mediasession = MediaSessionCompat(baseContext,"My Music")
        return myBinder
    }

    inner class MyBinder:Binder(){
        fun currService():MusicService{
            return this@MusicService
        }
    }


    fun showNotifiaction(playPause:Int){

        val prevIntent =Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.PREV)
        val prevpendingIntent =PendingIntent.getBroadcast(baseContext,0,prevIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT )


        val nextIntent =Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextpendingIntent =PendingIntent.getBroadcast(baseContext,0,nextIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        val playIntent =Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
        val playpendingIntent =PendingIntent.getBroadcast(baseContext,0,playIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        val exitIntent =Intent(baseContext,NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
        val exitpendingIntent =PendingIntent.getBroadcast(baseContext,0,exitIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val imgArt = getImgArt(PlayerActivity.musicPA[PlayerActivity.songPosition].path)
        val image = if (imgArt!=null){
            BitmapFactory.decodeByteArray(imgArt,0,imgArt.size)
        }
        else BitmapFactory.decodeResource(resources,R.drawable.music_icon)

        val notification =NotificationCompat.Builder(baseContext,ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicPA[PlayerActivity.songPosition].title)
            .setContentText(PlayerActivity.musicPA[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.playlist)
            .setLargeIcon(image)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediasession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.left_arrow,"Previous",prevpendingIntent)
            .addAction(playPause,"Play",playpendingIntent)
            .addAction(R.drawable.next,"Next",nextpendingIntent)
            .addAction(R.drawable.close,"Exit",exitpendingIntent)
            .build()

        startForeground(13,notification)
    }


}
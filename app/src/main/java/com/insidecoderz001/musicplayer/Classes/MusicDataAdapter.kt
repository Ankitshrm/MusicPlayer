package com.insidecoderz001.musicplayer.Classes

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import com.insidecoderz001.musicplayer.PlayerActivity
import com.insidecoderz001.musicplayer.R
import java.util.concurrent.TimeUnit

data class MusicDataAdapter(val id:String ,val title:String,val album :String ,val artist:String,val duration :Long,val path:String,val artUri:String)

fun formatDuration(duration: Long):String{
    val minute =TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val second =(TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) -minute*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))

    return String.format("%02d:%02d",minute,second)

}


fun getImgArt(path:String):ByteArray?{
    val retriever =MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture

}

fun checksongPosition(inc:Boolean) {
    if (inc) {
        if (PlayerActivity.musicPA.size - 1 == PlayerActivity.songPosition) {
            PlayerActivity.songPosition = 0
        } else {
            ++PlayerActivity.songPosition
        }
    } else {
        if (0 == PlayerActivity.songPosition) {
            PlayerActivity.songPosition = PlayerActivity.musicPA.size - 1
        } else {
            --PlayerActivity.songPosition
        }
    }
}

    fun createMediaPlayer(){
        try {
            if (PlayerActivity.musicService!!.mediaPlayer == null) {
                PlayerActivity.musicService!!.mediaPlayer = MediaPlayer()
            }
            PlayerActivity.musicService!!.mediaPlayer!!.reset()
            PlayerActivity.musicService!!.mediaPlayer!!.setDataSource(PlayerActivity.musicPA[PlayerActivity.songPosition].path)
            PlayerActivity.musicService!!.mediaPlayer!!.prepare()
//            PlayerActivity.musicService!!.mediaPlayer!!.start()
//            PlayerActivity.isPlaying =true
            PlayerActivity.binding.playPause.setImageResource(R.drawable.ic_baseline_pause_24)
            PlayerActivity.musicService!!.showNotifiaction(R.drawable.ic_baseline_pause_24)

        }catch (e:Exception){
            return
        }
    }


package com.insidecoderz001.musicplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.insidecoderz001.musicplayer.Classes.MusicDataAdapter
import com.insidecoderz001.musicplayer.Classes.MusicService
import com.insidecoderz001.musicplayer.Classes.checksongPosition
import com.insidecoderz001.musicplayer.Classes.createMediaPlayer
import com.insidecoderz001.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(),ServiceConnection {

    companion object{
        lateinit var musicPA :ArrayList<MusicDataAdapter>
         var songPosition:Int =0
         var isPlaying :Boolean = false
         var musicService :MusicService?=null
        lateinit var binding:ActivityPlayerBinding
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ids()

        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
        init()

        binding.playPause.setOnClickListener {

            if(isPlaying) pauseMusic()
            else playMusic()
        }

        binding.nextSong.setOnClickListener {
            prevNextSong(true)
        }

        binding.prevSong.setOnClickListener {
            prevNextSong(false)
        }



    }

     private fun prevNextSong(inc:Boolean){
        if (inc){
            checksongPosition(true)
//            ++songPosition
            setLayout()
            createMediaPlayer()

        }else
        {
            checksongPosition(false)
//            --songPosition
            setLayout()
            createMediaPlayer()
        }
    }









    private fun init(){
        songPosition  =intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter" ->{
                musicPA= ArrayList()
                musicPA.addAll(MainActivity.musicMA)
                setLayout()

            }

            "MainActivity" ->{
                musicPA= ArrayList()
                musicPA.addAll(MainActivity.musicMA)
                musicPA.shuffle()
                setLayout()
            }
        }
    }




    private fun playMusic(){
        binding.playPause.setImageResource(R.drawable.ic_baseline_pause_24)
        musicService!!.showNotifiaction(R.drawable.ic_baseline_pause_24)
        isPlaying=true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic(){
        binding.playPause.setImageResource(R.drawable.play)
        musicService!!.showNotifiaction(R.drawable.play )
        isPlaying=false
        musicService!!.mediaPlayer!!.pause()
    }

    private fun setLayout(){
        Glide.with(this)
            .load(musicPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_icon).centerCrop())
            .into(binding.songImg)
        binding.songName.text = musicPA[songPosition].title
    }

    private fun ids() {
        binding= ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService=binder.currService()
        createMediaPlayer()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService =null
    }
}
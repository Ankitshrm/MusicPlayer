package com.insidecoderz001.musicplayer.Classes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.insidecoderz001.musicplayer.PlayerActivity
import com.insidecoderz001.musicplayer.R
import com.insidecoderz001.musicplayer.databinding.ListSongLayoutBinding

class MusicAdapter(private val context:Context ,private val musicList:ArrayList<MusicDataAdapter>): RecyclerView.Adapter<MusicAdapter.MyHolder>() {

    class MyHolder(binding: ListSongLayoutBinding): RecyclerView.ViewHolder(binding.root){
        val title =binding.songTitle
        val album =binding.songDes
        val img =binding.songImg
        val duration =binding.duration
        val root =binding.root
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyHolder {
        return MyHolder(ListSongLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MusicAdapter.MyHolder, position: Int) {
        holder.title.text =musicList[position].title
        holder.album.text =musicList[position].album
        holder.duration.text = formatDuration(musicList[position].duration)
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.music_icon).centerCrop())
            .into(holder.img )

        holder.root.setOnClickListener {
            val intent =Intent(context,PlayerActivity::class.java)
            intent.putExtra("index",position)
            intent.putExtra("class","MusicAdapter")
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}
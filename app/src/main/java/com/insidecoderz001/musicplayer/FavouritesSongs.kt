package com.insidecoderz001.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.insidecoderz001.musicplayer.databinding.ActivityFavouritesSongsBinding

class FavouritesSongs : AppCompatActivity() {

    private lateinit var binding: ActivityFavouritesSongsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
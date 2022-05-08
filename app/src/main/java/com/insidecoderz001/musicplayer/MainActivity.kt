package com.insidecoderz001.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.insidecoderz001.musicplayer.Classes.MusicAdapter
import com.insidecoderz001.musicplayer.Classes.MusicDataAdapter
import com.insidecoderz001.musicplayer.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var musicMA:ArrayList<MusicDataAdapter>
    }


    private lateinit var binding : ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter
//    private lateinit var toggle :ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestStorage()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicMA =getAudio()


//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
//        toggle = ActionBarDrawerToggle(this@MainActivity,binding.root,R.string.open,R.string.close)
//        binding.root.addDrawerListener(toggle)
//        toggle.syncState()

        binding.apply {
            requestStorage()
            recyclerSongs.setHasFixedSize(true)
            recyclerSongs.setItemViewCacheSize(10)
            recyclerSongs.layoutManager=LinearLayoutManager(this@MainActivity)
            musicAdapter =MusicAdapter(this@MainActivity, musicMA)
            recyclerSongs.adapter=musicAdapter
            txtAllSong.text = "Total Songs : "+musicAdapter.itemCount


            shuffleBtn.setOnClickListener {
                val intent =Intent(this@MainActivity,PlayerActivity::class.java)
                intent.putExtra("index",0)
                intent.putExtra("class","MainActivity")
                startActivity(intent)
            }
            Favourites.setOnClickListener {
                startActivity(Intent(this@MainActivity,FavouritesSongs::class.java))
            }
            Playlist.setOnClickListener {
                startActivity(Intent(this@MainActivity,PlayListActivity::class.java))
            }
        }

        getAudio()
    }

    @SuppressLint("Recycle", "Range")
    private fun getAudio():ArrayList<MusicDataAdapter> {
        val tempList =ArrayList<MusicDataAdapter>()
        val selection =MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val projection = arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID )
        val cursor =this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,
            MediaStore.Audio.Media.DATE_ADDED,null)
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    val title =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val id =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val album =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artist =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val duration =cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val path =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val albumId =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri =Uri.parse("content://media/external/audio/albumart")
                    val artUri= Uri.withAppendedPath(uri,albumId).toString()

                    val music =MusicDataAdapter(id,title,album,artist,duration,path,artUri)
                    val file=File(music.path)
                    if(file.exists()) tempList.add(music)
                }while (cursor.moveToNext())
                cursor.close()
            }
        }
        return tempList


    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (toggle.onOptionsItemSelected(item)) return true
//        return super.onOptionsItemSelected(item)
//    }

    private fun requestStorage() {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==11){
            if(!grantResults.isEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
            }
        }
    }




}
package hs.project.secondweek

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.MusicListData
import hs.project.secondweek.databinding.ActivityListmusicBinding
import java.io.File

class ListMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListmusicBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusicListAdapter
    private lateinit var dataArray: ArrayList<MusicInfoData>
    lateinit var img: Array<Int>
    lateinit var musicTitle: Array<String>
    lateinit var musicSinger: Array<String>
    lateinit var musicTime: Array<String>

    companion object {
        const val TAG: String = "MYLOG"
        val MusicListMA : ArrayList<MusicInfoData> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "ListMusicActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 제목 흐르게 세팅
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
    }

    override fun onStart() {
        Log.d(TAG, "ListMusicActivity - onStart() 호출")
        super.onStart()
        initializeData()
    }

    override fun onResume() {
        Log.d(TAG, "ListMusicActivity - onResume() 호출")
        super.onResume()
        initializeLayout()
    }

    override fun onPause() {
        Log.d(TAG, "ListMusicActivity - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "ListMusicActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "ListMusicActivity - onRestart() 호출")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "ListMusicActivity - onDestroy() 호출")
        super.onDestroy()
    }

    private fun initializeData() {
        Log.d(TAG, "ListMusicActivity - 데이터 초기화")
        dataArray = getMusic()

    }

    private fun initializeLayout() {
        Log.d(TAG, "ListMusicActivity - 레이아웃 초기화")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.musicListSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        adapter = MusicListAdapter(this, dataArray)
        recyclerView.adapter = adapter
    }

    @SuppressLint("Recycle", "Range")
    private fun getMusic(): ArrayList<MusicInfoData> {
        val tempList = ArrayList<MusicInfoData>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA
        )
        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection, selection, null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null
        )

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val music = MusicInfoData(
                        id = idC,
                        title = titleC,
                        album = albumC,
                        artist = artistC,
                        path = pathC,
                        duration = durationC
                    )
                    val file = File(music.path)
                    if(file.exists()) {
                        tempList.add(music)
                    }
                } while (cursor.moveToNext())
            cursor.close()
        }
        return tempList
    }

    override fun onBackPressed() {
        Log.d(TAG, "ListMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }
}
package hs.project.secondweek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Adapter.VideoMusicAdapter
import hs.project.secondweek.Data.MusicListData
import hs.project.secondweek.databinding.ActivityListmusicBinding

class ListMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListmusicBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusicListAdapter
    private lateinit var dataArray: ArrayList<MusicListData>
    lateinit var img : Array<Int>
    lateinit var musicTitle : Array<String>
    lateinit var musicSinger : Array<String>
    lateinit var musicTime : Array<String>

    companion object {
        const val TAG: String = "MYLOG"
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
        dataArray = arrayListOf<MusicListData>()

        img = arrayOf(
            R.drawable.video_1, R.drawable.video_2, R.drawable.video_3, R.drawable.video_4
        )

        musicTitle = arrayOf(
            "아는동생", "SMARTPHONE", "Attention", "FOREVER 1"
        )

        musicSinger = arrayOf(
            "은비디아", "YENA (최예나)", "NewJeans", "소녀시대 (GIRLS' GENERATION)"
        )

        musicTime = arrayOf("0", "0", "0", "0")

        for (i in img.indices) {
            val musicData = MusicListData(img[i], musicTitle[i], musicSinger[i], musicTime[i])
            dataArray.add(musicData)
        }
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

    override fun onBackPressed() {
        Log.d(TAG, "ListMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }
}
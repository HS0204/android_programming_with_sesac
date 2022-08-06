package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.Data.NewMusicData
import hs.project.secondweek.Data.RecommendedMusicData
import hs.project.secondweek.Data.VideoMusicData
import hs.project.secondweek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val mainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedMusicAdapter : RecommendedMusicAdapter
    private lateinit var recommendedMusicDataArray: ArrayList<RecommendedMusicData>
    lateinit var recommendedMusicImg : Array<Int>
    lateinit var recommendedTxtTitle : Array<String>

    private lateinit var newMusicRecyclerView: RecyclerView
    private lateinit var newMusicAdapter: NewMusicAdapter
    private lateinit var newMusicDataArray: ArrayList<NewMusicData>
    lateinit var newImg : Array<Int>
    lateinit var newTxtTitle : Array<String>
    lateinit var newTxtSinger : Array<String>

    private lateinit var videoMusicRecyclerView: RecyclerView
    private lateinit var videoMusicAdapter: VideoMusicAdapter
    private lateinit var videoMusicDataArray: ArrayList<VideoMusicData>
    lateinit var videoImg : Array<Int>
    lateinit var videoTxtTitle : Array<String>
    lateinit var videoTxtSinger : Array<String>

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity - onCreate() 호출")
        setContentView(mainBinding.root)

        mainBinding.bottomNavigation.selectedItemId = R.id.menu_home
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        // 음악 제목, 가수 이름 흐르게
        mainBinding.musicTitle.isSingleLine = true
        mainBinding.musicTitle.isSelected = true
        mainBinding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        mainBinding.musicSinger.isSingleLine = true
        mainBinding.musicSinger.isSelected = true
        mainBinding.musicSinger.ellipsize = TextUtils.TruncateAt.MARQUEE

        // 음악 재생 activity 인텐트
        mainBinding.musicPlayerSection.setOnClickListener {
            val intent = Intent(this, PlayerMusicActivity::class.java)
            intent.putExtra("musicTitle", "${mainBinding.musicTitle.text}")
            intent.putExtra("musicSinger", "${mainBinding.musicSinger.text}")
            startActivity(intent)
        }

        dataInitialize()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() 호출")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() 호출")

        // 추천 곡
        val recommendLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedRecyclerView = mainBinding.recommendedMusicSection
        recommendedRecyclerView.layoutManager = recommendLayoutManager
        recommendedRecyclerView.setHasFixedSize(true)

        recommendedMusicAdapter = RecommendedMusicAdapter(recommendedMusicDataArray)
        recommendedRecyclerView.adapter = recommendedMusicAdapter

        // 최신 곡
        val newLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        newMusicRecyclerView = mainBinding.newMusicSection
        newMusicRecyclerView.layoutManager = newLayoutManager
        newMusicRecyclerView.setHasFixedSize(true)

        newMusicAdapter = NewMusicAdapter(newMusicDataArray)
        newMusicRecyclerView.adapter = newMusicAdapter

        // 영상
        val videoLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        videoMusicRecyclerView = mainBinding.videoMusicSection
        videoMusicRecyclerView.layoutManager = videoLayoutManager
        videoMusicRecyclerView.setHasFixedSize(true)

        videoMusicAdapter = VideoMusicAdapter(videoMusicDataArray)
        videoMusicRecyclerView.adapter = videoMusicAdapter
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity - onPause() 호출")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() 호출")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity - onRestart() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy() 호출")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "MainActivity - onActivityResult() 호출")

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity - 홈 클릭")

            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MainActivity - 뮤직4U 클릭")
                val intent = Intent(this, Music4uActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MainActivity - 내 음악 클릭")
                val intent = Intent(this, MymusicActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "MainActivity - 탐색 클릭")
                val intent = Intent(this, SearchingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_always -> {
                Log.d(TAG, "MainActivity - 24/7 클릭")
                val intent = Intent(this, AlwaysActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "MainActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }

    private fun dataInitialize() {
        Log.d(TAG, "MainActivity - 데이터 초기화")

        // 추천 곡
        recommendedMusicDataArray = arrayListOf<RecommendedMusicData>()

        recommendedMusicImg = arrayOf(
            R.drawable.station_1, R.drawable.station_2, R.drawable.station_3, R.drawable.station_4,
            R.drawable.station_5
        )

        recommendedTxtTitle = arrayOf(
            "Kygo, Steve Aoki, HRVY", "Lucia의 팬이라면", "수현, 플레이문, Lubless", "Anne-Marie, 마마무, 베이식",
            "The 5th Mini Album 'Can ...'"
        )

        for (i in recommendedMusicImg.indices) {
            val recommendedMusicData = RecommendedMusicData(recommendedMusicImg[i], recommendedTxtTitle[i])
            recommendedMusicDataArray.add(recommendedMusicData)
        }

        // 최신 곡
        newMusicDataArray = arrayListOf<NewMusicData>()

        newImg = arrayOf(
            R.drawable.new_1, R.drawable.new_2, R.drawable.new_3, R.drawable.new_4, R.drawable.new_5,
            R.drawable.new_6, R.drawable.new_7, R.drawable.new_8, R.drawable.new_9, R.drawable.new_10,
            R.drawable.new_11, R.drawable.new_12, R.drawable.new_13, R.drawable.new_14, R.drawable.new_15,
            R.drawable.new_16, R.drawable.new_17, R.drawable.new_18
        )

        newTxtTitle = arrayOf(
            "요즘 신곡", "Fried Egg", "FOREVER 1-...", "Bad Decisions", "Still Sunset",
            "아는동생", "오늘부터...", "화염방", "Live Love Dre...", "묘 (Cat)",
            "사랑은 늘 그렇...", "All 4 Nothing", "Funk Wav Bo...", "Blue", "Dance all night",
            "First Wave", "청춘스타 Part12", "자기야 사랑해"
        )

        newTxtSinger = arrayOf(
            "화/금 업데이트", "EB", "소녀시대", "benny blanco, 방...", "넬",
            "은비디아", "홍이삭", "탁", "션케이", "Colde",
            "라운드어바웃", "Lauv", "Calvin Harris", "현성", "방민혁",
            "Ordinary Surfers", "종한, 김푸름 외", "COVA"
        )

        for (i in newImg.indices) {
            val newMusicData = NewMusicData(newImg[i], newTxtTitle[i], newTxtSinger[i])
            newMusicDataArray.add(newMusicData)
        }

        // 영상
        videoMusicDataArray = arrayListOf<VideoMusicData>()

        videoImg = arrayOf(
            R.drawable.video_1, R.drawable.video_2, R.drawable.video_3, R.drawable.video_4
        )

        videoTxtTitle = arrayOf(
            "아는동생", "SMARTPHONE", "Attention", "FOREVER 1"
        )

        videoTxtSinger = arrayOf(
            "은비디아", "YENA (최예나)", "NewJeans", "소녀시대 (GIRLS' GENERATION)"
        )

        for (i in videoImg.indices) {
            val videoMusicData = VideoMusicData(videoImg[i], videoTxtTitle[i], videoTxtSinger[i])
            videoMusicDataArray.add(videoMusicData)
        }
    }

}
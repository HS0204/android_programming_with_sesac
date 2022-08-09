package hs.project.secondweek

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.Adapter.NewMusicAdapter
import hs.project.secondweek.Adapter.RecommendedMusicAdapter
import hs.project.secondweek.Adapter.VideoMusicAdapter
import hs.project.secondweek.Adapter.mediaPlayer
import hs.project.secondweek.Data.NewMusicData
import hs.project.secondweek.Data.RecommendedMusicData
import hs.project.secondweek.Data.VideoMusicData
import hs.project.secondweek.MainActivity.Companion.PlayN
import hs.project.secondweek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedMusicAdapter: RecommendedMusicAdapter
    private lateinit var recommendedMusicDataArray: ArrayList<RecommendedMusicData>
    lateinit var recommendedMusicImg: Array<Int>
    lateinit var recommendedTxtTitle: Array<String>

    private lateinit var newMusicRecyclerView: RecyclerView
    private lateinit var newMusicAdapter: NewMusicAdapter
    private lateinit var newMusicDataArray: ArrayList<NewMusicData>
    lateinit var newImg: Array<Int>
    lateinit var newTxtTitle: Array<String>
    lateinit var newTxtSinger: Array<String>

    private lateinit var videoMusicRecyclerView: RecyclerView
    private lateinit var videoMusicAdapter: VideoMusicAdapter
    private lateinit var videoMusicDataArray: ArrayList<VideoMusicData>
    lateinit var videoImg: Array<Int>
    lateinit var videoTxtTitle: Array<String>
    lateinit var videoTxtSinger: Array<String>

    val PERMISSION_CODE = 100

    companion object {
        const val TAG: String = "MYLOG"

        var TitleN: TextView? = null
        var ArtistN: TextView? = null
        var PlayN: ImageView? = null
        var List: ImageView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity - onCreate() 호출")
        setContentView(binding.root)

        TitleN = binding.musicTitle
        ArtistN = binding.musicSinger
        PlayN = binding.musicControl
        List = binding.musicList

        //requestRuntimePermission()
        requestPermission()

        binding.bottomNavigation.selectedItemId = R.id.menu_home
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        PlayN?.setOnClickListener {
            Log.d(TAG, "MainActivity -> 음악 컨트롤 버튼 클릭")
            if (mediaPlayer == null) {
                Toast.makeText(this, "리스트 버튼을 클릭해 음악을 선택해주세요", Toast.LENGTH_SHORT).show().toString()
            } else if (mediaPlayer!!.isPlaying)
                pauseMusic()
            else if (mediaPlayer != null)
                playMusic()
        }

        binding.musicPlayerSection.setOnClickListener {
            Log.d("MYLOG", "MainActivity -> 하단 음악 바를 클릭")
            if (mediaPlayer == null) {
                Toast.makeText(this, "리스트 버튼을 클릭하여 음악을 선택해주세요", Toast.LENGTH_SHORT).show()
                    .toString()
            } else {
                val intent = Intent(this, PlayerMusicActivity::class.java)
                startActivity(intent)
            }
        }

        // 음악 리스트 activity 인텐트
        binding.musicList.setOnClickListener {
            val intent = Intent(this, ListMusicActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() 호출")
        //if (requestRuntimePermission())
            initializeData()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() 호출")
        //if (requestRuntimePermission())
            initializeLayout()

        // onCreate()에서 초기화를 하면 다시 MainActivity로 돌아왔을 때 실행이 되지 않는다
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        binding.musicSinger.isSingleLine = true
        binding.musicSinger.isSelected = true
        binding.musicSinger.ellipsize = TextUtils.TruncateAt.MARQUEE
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

    private fun initializeData() {
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
            val recommendedMusicData =
                RecommendedMusicData(recommendedMusicImg[i], recommendedTxtTitle[i])
            recommendedMusicDataArray.add(recommendedMusicData)
        }

        // 최신 곡
        newMusicDataArray = arrayListOf<NewMusicData>()

        newImg = arrayOf(
            R.drawable.new_1,
            R.drawable.new_2,
            R.drawable.new_3,
            R.drawable.new_4,
            R.drawable.new_5,
            R.drawable.new_6,
            R.drawable.new_7,
            R.drawable.new_8,
            R.drawable.new_9,
            R.drawable.new_10,
            R.drawable.new_11,
            R.drawable.new_12,
            R.drawable.new_13,
            R.drawable.new_14,
            R.drawable.new_15,
            R.drawable.new_16,
            R.drawable.new_17,
            R.drawable.new_18
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

    private fun initializeLayout() {
        Log.d(TAG, "MainActivity - 레이아웃 초기화")

        // 추천 곡
        val recommendLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendedRecyclerView = binding.recommendedMusicSection
        recommendedRecyclerView.layoutManager = recommendLayoutManager
        recommendedRecyclerView.setHasFixedSize(true)

        recommendedMusicAdapter = RecommendedMusicAdapter(this, recommendedMusicDataArray)
        recommendedRecyclerView.adapter = recommendedMusicAdapter

        // 최신 곡
        val newLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        newMusicRecyclerView = binding.newMusicSection
        newMusicRecyclerView.layoutManager = newLayoutManager
        newMusicRecyclerView.setHasFixedSize(true)

        newMusicAdapter = NewMusicAdapter(this, newMusicDataArray)
        newMusicRecyclerView.adapter = newMusicAdapter

        // 영상
        val videoLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        videoMusicRecyclerView = binding.videoMusicSection
        videoMusicRecyclerView.layoutManager = videoLayoutManager
        videoMusicRecyclerView.setHasFixedSize(true)

        videoMusicAdapter = VideoMusicAdapter(this, videoMusicDataArray)
        videoMusicRecyclerView.adapter = videoMusicAdapter
    }

    private fun requestRuntimePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
            return false
        }
        return true
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "저장소 접근 허용", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "저장소 접근 불가", Toast.LENGTH_SHORT).show()
                }

                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "핸드폰 상태 접근 허용", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "핸드폰 상태 접근 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pauseMusic() {
        PlayN?.setImageResource(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }

    private fun playMusic() {
        PlayN?.setImageResource(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity - 홈 클릭")

            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MainActivity - 뮤직4U 클릭")
                val intent = Intent(this, Music4uActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MainActivity - 내 음악 클릭")
                val intent = Intent(this, MymusicActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "MainActivity - 탐색 클릭")
                val intent = Intent(this, SearchingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            R.id.menu_always -> {
                Log.d(TAG, "MainActivity - 24/7 클릭")
                val intent = Intent(this, AlwaysActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "MainActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }

}
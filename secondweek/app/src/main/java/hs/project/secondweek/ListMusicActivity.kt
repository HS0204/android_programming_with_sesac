package hs.project.secondweek

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Service.MusicService
import hs.project.secondweek.databinding.ActivityListmusicBinding

// 서비스
private var player: MusicService? = null
var serviceBound = false

class ListMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListmusicBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    // private lateinit var dieAdapter: MusicListAdapter

    companion object {
        const val TAG: String = "MYLOG"

        var Title: TextView? = null
        var Artist: TextView? = null
        var Play: ImageView? = null
        var Cover: ImageView? = null

        var ongoingCall = false
        var phoneStateListener: PhoneStateListener? = null
        var telephonyManager: TelephonyManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "ListMusicActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAdapter()

        Title = binding.musicTitle
        Artist = binding.musicSinger
        Play = binding.musicControl
        Cover = binding.musicList

        Play?.setOnClickListener {
            Log.d(TAG, "ListMusicActivity -> 음악 컨트롤 버튼 클릭")
            if (mediaPlayer!!.isPlaying)
                pauseMusic()
            else if (mediaPlayer != null)
                playMusic()
        }

        binding.musicPlayerSection.setOnClickListener {
            Log.d(TAG, "ListMusicActivity -> 하단 음악 바 클릭")
            if (mediaPlayer != null) {
                val intent = Intent(this, PlayerMusicActivity::class.java)
                startActivity(intent)
            } else {
                MainActivity
            }
        }

        binding.musicList.setOnClickListener {
            if (customMusicList.size > 0) {
                val intent = Intent(this, CustomListMusicActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "먼저 음악을 추가해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        Log.d(TAG, "ListMusicActivity - onStart() 호출")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "ListMusicActivity - onResume() 호출")
        super.onResume()
        initializeMiniPlayer()
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

    private fun setAdapter() {
        Log.d(TAG, "ListMusicActivity - RecyclerView 어댑터 세팅")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.musicListSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        musicListAdapter = MusicListAdapter(this, localMusicList, false)
        recyclerView.adapter = musicListAdapter

    }

    private fun initializeMiniPlayer() {
        Log.d(TAG, "ListMusicActivity - 미니 플레이어 초기화")
        Title?.text = changeTextTitle
        Artist?.text = changeTextArtist
        Title?.isSingleLine = true
        Title?.isSelected = true
        Title?.ellipsize = TextUtils.TruncateAt.MARQUEE

        if (mediaPlayer == null)
            Play?.setImageResource(R.drawable.icon_playing)
        else if (mediaPlayer!!.isPlaying) {
            Play?.setImageResource(R.drawable.icon_pause)
        }

    }

    private fun pauseMusic() {
        Play?.setImageResource(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }

    private fun playMusic() {
        Play?.setImageResource(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }

    override fun onBackPressed() {
        Log.d(TAG, "ListMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }
}
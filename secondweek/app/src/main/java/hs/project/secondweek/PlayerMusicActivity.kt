package hs.project.secondweek

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SeekBar
import hs.project.secondweek.databinding.ActivityPlayermusicBinding

class PlayerMusicActivity : AppCompatActivity() {

    private val playermusicBinding by lazy { ActivityPlayermusicBinding.inflate(layoutInflater) }

    private lateinit var player: MediaPlayer
    private val currentMusic = mutableListOf(R.raw.music)

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onCreate(savedInstanceState)
        setContentView(playermusicBinding.root)

        // 제목 흐르게 세팅
        playermusicBinding.musicTitle.isSingleLine = true
        playermusicBinding.musicTitle.isSelected = true
        playermusicBinding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "PlayerMusicActivity - onStart() 호출")
        loadMusic()
        createMusicPlayer(currentMusic[0])
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "PlayerMusicActivity - onResume() 호출")
        initialiseSeekBar()
        controlMusicPlayer()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "PlayerMusicActivity - onPause() 호출")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "PlayerMusicActivity - onStop() 호출")
        releasePlayer()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "PlayerMusicActivity - onRestart() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PlayerMusicActivity - onDestroy() 호출")

    }

    private fun createMusicPlayer(musicId: Int) {
        player = MediaPlayer.create(this, musicId)
        player.isLooping = true
        Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 $player 생성, 음악 ${player.audioSessionId}")
    }

    private fun controlMusicPlayer() {
        Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 $player 대기")
        val controlBtn = playermusicBinding.controlIcon
        val repeatBtn = playermusicBinding.repeatIcon
        val seekBar = playermusicBinding.musicBar

        controlBtn.setOnClickListener {
            if(!player.isPlaying) {
                Log.d(TAG, "PlayerMusicActivity - 재생 버튼 클릭")
                player.start()
                controlBtn.setImageResource(R.drawable.icon_pause)
            }
            else {
                Log.d(TAG, "PlayerMusicActivity - 일시정지 버튼 클릭")
                player.pause()
                controlBtn.setImageResource(R.drawable.icon_playing)
            }
        }

        repeatBtn.setOnClickListener {
            if(!player.isLooping) {
                Log.d(TAG, "PlayerMusicActivity - 한 곡 루프 돌게 버튼 클릭")
                player.isLooping = true
                repeatBtn.setImageResource(R.drawable.icon_repeat_one_music)
            }
            else {
                Log.d(TAG, "PlayerMusicActivity - 리스트 루프 돌게 버튼 클릭")
                player.isLooping = false
                repeatBtn.setImageResource(R.drawable.icon_repeat_list_once)
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private fun initialiseSeekBar() {
        Log.d(TAG, "PlayerMusicActivity - initialiseSeekBar() 호출")
        val controlBtn = playermusicBinding.controlIcon
        val seekBar = playermusicBinding.musicBar
        seekBar.max = player.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    seekBar.progress = player.currentPosition
                    handler.postDelayed(this, 1000)
                    if(!player.isLooping && seekBar.progress == seekBar.max){
                        seekBar.progress = 0
                        controlBtn.setImageResource(R.drawable.icon_playing)
                    }
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        },0)
    }

    private fun loadMusic() {
        val musicTitle = intent.getStringExtra("musicTitle")
        val musicSinger = intent.getStringExtra("musicSinger")

        playermusicBinding.musicTitle.text = musicTitle
        playermusicBinding.musicSinger.text = musicSinger
    }

    private fun releasePlayer() {
        player.stop()
        player.reset()
        player.release()
    }

    override fun onBackPressed() {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}
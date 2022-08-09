package hs.project.secondweek

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Adapter.*
import hs.project.secondweek.Service.MusicService
import hs.project.secondweek.databinding.ActivityPlayermusicBinding

var totalTime: Int = 0

class PlayerMusicActivity : AppCompatActivity(), ServiceConnection {

    private val binding by lazy { ActivityPlayermusicBinding.inflate(layoutInflater) }

    var isShuffle: Boolean = false

    companion object {
        const val TAG: String = "MYLOG"

        var seekbar: SeekBar? = null
        var playBtn: ImageView? = null

        var musicService: MusicService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        seekbar = binding.musicBar
        playBtn = binding.controlIcon
        totalTime = mediaPlayer!!.duration

        binding.musicBar.max = mediaPlayer!!.duration
        binding.musicBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser)
                        mediaPlayer!!.seekTo(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            }
        )

        binding.controlIcon.setOnClickListener { controlMusic() }

        binding.previousIcon.setOnClickListener { moveMusic(increment = false) }
        binding.nextIcon.setOnClickListener { moveMusic(increment = true) }

        binding.repeatIcon.setOnClickListener {        }

        binding.shuffleIcon.setOnClickListener {        }

        binding.musicList.setOnClickListener {
            val intent = Intent(this, ListMusicActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "PlayerMusicActivity - onStart() 호출")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "PlayerMusicActivity - onResume() 호출")
        initializeLayout()
        initializeSeekBar()

        if (mediaPlayer!!.isPlaying)
            playBtn?.setImageResource(R.drawable.icon_pause)
        else
            playBtn?.setImageResource(R.drawable.icon_playing)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "PlayerMusicActivity - onPause() 호출")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "PlayerMusicActivity - onStop() 호출")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "PlayerMusicActivity - onRestart() 호출")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PlayerMusicActivity - onDestroy() 호출")
        finish()
    }

    private fun initializeLayout() {
        Log.d(TAG, "PlayerMusicActivity - 레이아웃 초기화")
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        binding.musicTitle.text = changeTextTitle
        binding.musicSinger.text = changeTextArtist
        Glide.with(this).load(myListTrack[musicPosition].artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art1).fitCenter())
            .into(binding.albumArt)
    }

    private fun initializeSeekBar() {
        Log.d(TAG, "PlayerMusicActivity - seekBar 초기화")
        val controlBtn = binding.controlIcon
        val seekBar = binding.musicBar
        seekBar.max = mediaPlayer!!.duration

        val handler = Handler()
        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)

                    settingMusicTime()
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        },0)
    }

    private fun musicTimeCal(time: Int): String {
        lateinit var timeLabel: String
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = if (min < 10 ) {
            "0$min:"
        } else {
            "$min:"
        }
        if (sec < 10) {
            timeLabel += "0"
        }
        timeLabel += sec

        return timeLabel
    }

    private fun settingMusicTime() {
        var currentTime = musicTimeCal(mediaPlayer!!.currentPosition)
        var remainingTime = musicTimeCal((mediaPlayer!!.duration) - mediaPlayer!!.currentPosition)

        binding.musicCurrentTime.text = currentTime
        binding.musicRemainingTime.text = remainingTime

        if (!mediaPlayer!!.isPlaying && seekbar?.progress!! == 0) {
            binding.musicCurrentTime.text = "00:00"
            binding.musicRemainingTime.text = musicTimeCal(mediaPlayer!!.duration)
        }
    }

    private fun controlMusic() {
        if (mediaPlayer!!.isPlaying) {
            setPauseIcon()
            mediaPlayer!!.pause()
        }
        else {
            setPlayingIcon()
            mediaPlayer!!.start()
        }
    }

    private fun onShuffle() {
        Log.d(TAG, "PlayerMusicActivity - 셔플 ON")
        binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_on)
        isShuffle = true
        myListTrack.shuffle()
    }
    private fun offShuffle() {
        Log.d(TAG, "PlayerMusicActivity - 셔플 OFF")
        binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_off)
        isShuffle = false
        initializeData()
    }

    private fun moveMusic(increment: Boolean) {
        if (increment){
            Log.d(TAG, "PlayerMusicActivity - Next 버튼 클릭")
            setMusicPosition(increment = true)
        }
        else{
            Log.d(TAG, "PlayerMusicActivity - Previous 버튼 클릭")
            setMusicPosition(increment = false)
        }

        resetMusic()
    }

    private fun resetMusic() {
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(myListTrack[musicPosition].path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        Log.d("MYLOG", "음악 플레이어 $mediaPlayer | 현재 곡 ${myListTrack[musicPosition].title}")

        changeTextTitle = myListTrack[musicPosition].title
        changeTextArtist = myListTrack[musicPosition].artist

        initializeLayout()

        MainActivity.TitleN?.text = changeTextTitle
        MainActivity.ArtistN?.text = changeTextArtist

        if (!mediaPlayer!!.isPlaying)
            setPauseIcon()
        else
            setPlayingIcon()
    }

    private fun setPauseIcon() {
        playBtn?.setImageResource(R.drawable.icon_playing)
        MainActivity.PlayN?.setImageResource(R.drawable.icon_playing)
        ListMusicActivity.Play?.setImageResource(R.drawable.icon_playing)
    }

    private fun setPlayingIcon() {
        playBtn?.setImageResource(R.drawable.icon_pause)
        MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)
        ListMusicActivity.Play?.setImageResource(R.drawable.icon_pause)
    }

    private fun setMusicPosition(increment: Boolean) {
        if (increment){
            if (myListTrack.size - 1 == musicPosition)
                musicPosition = 0
            else ++musicPosition
        }
        else{
            if (0 == musicPosition)
                musicPosition = myListTrack.size - 1
            else --musicPosition
        }
    }

    private fun loadMusicData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    }

    private fun saveMusicData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {

        }.apply()
    }




    private fun startService() {
        // 서비스 시작
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        //startService(intent)
    }

    private fun initializeData() {
        musicPosition = intent.getIntExtra("index", 0)
        Log.d(TAG, "PlayerMusicActivity - 데이터 초기화")
        myListTrack = ArrayList()
        myListTrack.addAll(myListTrack)
    }

    private fun TEMPinitializeLayout(shuffle: Boolean) {
        Log.d(TAG, "PlayerMusicActivity - 레이아웃 초기화")
        Glide.with(this).load(myListTrack[musicPosition].artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art1).fitCenter())
            .into(binding.albumArt)

        binding.musicTitle.text = myListTrack[musicPosition].title
        binding.musicSinger.text = myListTrack[musicPosition].artist

        if (shuffle) binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_on)
        else binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_off)
    }

    private fun releaseMusicPlayer() {
        Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 ${musicService!!.player} 삭제")
        musicService!!.player!!.release()
    }

    override fun onBackPressed() {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onBackPressed()

        overridePendingTransition(0,0)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "PlayerMusicActivity - 서비스 시작")
        val binder = service as MusicService.MusicBinder
        musicService = binder.currentService()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "PlayerMusicActivity - 서비스 종료")
        musicService = null
    }
}
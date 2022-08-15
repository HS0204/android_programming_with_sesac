package hs.project.secondweek

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eightbitlab.com.blurview.RenderScriptBlur
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.formatDuration
import hs.project.secondweek.Service.MusicService
import hs.project.secondweek.databinding.ActivityPlayermusicBinding

var totalTime: Int = 0

class PlayerMusicActivity : AppCompatActivity(), ServiceConnection {

    private val binding by lazy { ActivityPlayermusicBinding.inflate(layoutInflater) }
    val handler = Handler()

    var tempMusicList = ArrayList<MusicInfoData>()

    companion object {
        const val TAG: String = "MYLOG"

        var seekbar: SeekBar? = null
        var playBtn: ImageView? = null

        var isShuffle: Boolean = false

        var musicService: MusicService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        blurBackground()

        seekbar = binding.musicBar
        playBtn = binding.controlIcon
        totalTime = mediaPlayer!!.duration

        mediaPlayer!!.isLooping = false

        binding.controlIcon.setOnClickListener { controlMusic() }

        binding.previousIcon.setOnClickListener { moveMusic(increment = false) }
        binding.nextIcon.setOnClickListener { moveMusic(increment = true) }

        binding.repeatIcon.setOnClickListener {
            if (!mediaPlayer!!.isLooping) {
                Log.d(TAG, "PlayerMusicActivity - 한 곡 루프 되도록 버튼 클릭")
                mediaPlayer!!.isLooping = true
                binding.repeatIcon.setImageResource(R.drawable.icon_repeat_one_music)
            }
            else {
                Log.d(TAG, "PlayerMusicActivity - 리스트 루프 되도록 버튼 클릭")
                mediaPlayer!!.isLooping = false
                binding.repeatIcon.setImageResource(R.drawable.icon_repeat_list_once)
            }
        }

        binding.shuffleIcon.setOnClickListener {
            if (isShuffle) offShuffle()
            else onShuffle()
        }

        binding.musicList.setOnClickListener {
            val intent = Intent(this, CustomListMusicActivity::class.java)
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
        handler.removeMessages(0)
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

        binding.musicBar.max = mediaPlayer!!.duration
        binding.musicBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) mediaPlayer!!.seekTo(progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            }
        )

        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        binding.musicTitle.text = changeTextTitle
        binding.musicSinger.text = changeTextArtist

        setCover()
    }

    private fun blurBackground() {
        val radius = 22f
        val decorView = window.decorView
        val rootView: ViewGroup = decorView.findViewById(android.R.id.content)
        val windowBackground = decorView.background

        binding.blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    private fun setCover() {
        Glide.with(this).load(selectedMusic?.artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art).fitCenter())
            .into(binding.albumArt)

        Glide.with(this).load(selectedMusic?.artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art).centerCrop())
            .into(binding.blurAlbumArt)
    }

    private fun initializeSeekBar() {
        Log.d(TAG, "PlayerMusicActivity - seekBar 초기화")
        val controlBtn = binding.controlIcon
        val seekBar = binding.musicBar
        seekBar.max = mediaPlayer!!.duration

        handler.postDelayed(object: Runnable {
            override fun run() {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)

                    var currentTime = formatDuration((mediaPlayer!!.currentPosition).toLong())
                    var endTime = formatDuration((mediaPlayer!!.duration).toLong())

                    Log.d(TAG, "${currentTime} | ${endTime} | ${isShuffle}")
                    if (!mediaPlayer!!.isLooping && currentTime == endTime) {
                        Log.d(TAG, "진입")
                        moveMusic(true)
                    }
                    settingMusicTime()
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        },0)
    }

    private fun settingMusicTime() {
        binding.musicCurrentTime.text = formatDuration(mediaPlayer!!.currentPosition.toLong())
        binding.musicRemainingTime.text = formatDuration((mediaPlayer!!.duration - mediaPlayer!!.currentPosition).toLong())
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
        tempMusicList.addAll(customMusicList)
        customMusicList.shuffle()

        //shuffleMusicList = customMusicList
        //customMusicList = shuffleMusicList
    }

    private fun offShuffle() {
        Log.d(TAG, "PlayerMusicActivity - 셔플 OFF")
        binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_off)
        isShuffle = false
        customMusicList = tempMusicList
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
        val music = MusicInfoData(
            id = customMusicList[musicPosition].id,
            title = customMusicList[musicPosition].title,
            album = customMusicList[musicPosition].album,
            artist = customMusicList[musicPosition].artist,
            path = customMusicList[musicPosition].path,
            duration = customMusicList[musicPosition].duration,
            artUri = customMusicList[musicPosition].artUri
        )

        selectedMusic = music

        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(selectedMusic?.path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        seekbar?.max = mediaPlayer!!.duration
        Log.d("MYLOG", "음악 플레이어 $mediaPlayer | 현재 곡 ${selectedMusic?.title}")

        changeTextTitle = selectedMusic!!.title
        changeTextArtist = selectedMusic!!.artist

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
    }

    private fun setPlayingIcon() {
        playBtn?.setImageResource(R.drawable.icon_pause)
        MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)
    }

    private fun setMusicPosition(increment: Boolean) {
        if (increment){
            if (customMusicList.size - 1 == musicPosition)
                musicPosition = 0
            else ++musicPosition
        }
        else{
            if (0 == musicPosition)
                musicPosition = customMusicList.size - 1
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
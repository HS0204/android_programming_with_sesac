package hs.project.secondweek

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.databinding.ActivityPlayermusicBinding

class PlayerMusicActivity : AppCompatActivity(), ServiceConnection {

    private val binding by lazy { ActivityPlayermusicBinding.inflate(layoutInflater) }

    companion object {
        const val TAG: String = "MYLOG"

        lateinit var musicList: ArrayList<MusicInfoData>
        var musicPosition: Int = 0

        //lateinit var player: MediaPlayer
        var isPlaying: Boolean = true
        var isShuffling: Boolean = false

        var musicService: MusicService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.controlIcon.setOnClickListener {
            if (isPlaying) pauseMusic()
            else playMusic()
        }
        binding.shuffleIcon.setOnClickListener {
            if (isShuffling) offShuffle()
            else onShuffle()
        }
        binding.previousIcon.setOnClickListener { prevNextMusic(increment = false) }
        binding.nextIcon.setOnClickListener { prevNextMusic(increment = true) }
        binding.musicList.setOnClickListener {
            val intent = Intent(this, ListMusicActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        Log.d(TAG, "!!!!!!!!재진입")
        super.onNewIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "PlayerMusicActivity - onStart() 호출")
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        startService()
        initializeData()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "PlayerMusicActivity - onResume() 호출")
        initializeLayout(isShuffling)
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
        musicList = ArrayList()
        musicList.addAll(ListMusicActivity.dataArray)
    }

    private fun initializeLayout(shuffle: Boolean) {
        Log.d(TAG, "PlayerMusicActivity - 레이아웃 초기화")
        Glide.with(this).load(musicList[musicPosition].artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art1).centerCrop())
            .into(binding.albumArt)

        binding.musicTitle.text = musicList[musicPosition].title
        binding.musicSinger.text = musicList[musicPosition].artist

        if (shuffle) binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_on)
        else binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_off)
    }

    private fun createMusicPlayer() {
        try {
            musicService!!.player = MediaPlayer()

            musicService!!.player!!.reset()
            musicService!!.player!!.setDataSource(musicList[musicPosition].path)
            musicService!!.player!!.prepare()
            musicService!!.player!!.start()

            isPlaying = true
            binding.controlIcon.setImageResource(R.drawable.icon_pause)

            Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 ${musicService!!.player}에서 재생 음악 ${musicList[musicPosition].title}")
        }catch (e:Exception){return}
    }

    private fun playMusic() {
        binding.controlIcon.setImageResource(R.drawable.icon_pause)
        isPlaying = true
        musicService!!.player!!.start()
        Log.d(TAG, "playMusic - 음악 플레이어 ${musicService!!.player}에서 재생 음악 ${musicList[musicPosition].title}")
    }

    private fun pauseMusic() {
        binding.controlIcon.setImageResource(R.drawable.icon_playing)
        isPlaying = false
        musicService!!.player!!.pause()
    }

    private fun onShuffle() {
        Log.d(TAG, "PlayerMusicActivity - 셔플 ON")
        binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_on)
        isShuffling = true
        musicList.shuffle()
    }

    private fun offShuffle() {
        Log.d(TAG, "PlayerMusicActivity - 셔플 OFF")
        binding.shuffleIcon.setImageResource(R.drawable.icon_shuffle_off)
        isShuffling = false
        initializeData()
    }

    private fun prevNextMusic(increment: Boolean) {
        if (increment){
            Log.d(TAG, "PlayerMusicActivity - Next 버튼 클릭")
            setMusicPosition(increment = true)
            releaseMusicPlayer()
            initializeLayout(isShuffling)
            musicService!!.createMusicPlayer()
            //createMusicPlayer()
        }
        else{
            Log.d(TAG, "PlayerMusicActivity - Previous 버튼 클릭")
            setMusicPosition(increment = false)
            releaseMusicPlayer()
            initializeLayout(isShuffling)
            musicService!!.createMusicPlayer()
            //createMusicPlayer()
        }
    }

    private fun setMusicPosition(increment: Boolean) {
        if (increment){
            if (musicList.size - 1 == musicPosition)
                musicPosition = 0
            else ++musicPosition
        }
        else{
            if (0 == musicPosition)
                musicPosition = musicList.size - 1
            else --musicPosition
        }
    }

    private fun releaseMusicPlayer() {
        Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 ${musicService!!.player} 삭제")
        musicService!!.player!!.release()
    }

    override fun onBackPressed() {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        //super.onBackPressed()

        when (intent.getStringExtra("previousActivity")){
            "ListMusicActivity"->{
                val intent = Intent(this, ListMusicActivity::class.java)

                startActivity(intent)
            }
            "MainActivity"->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        overridePendingTransition(0,0)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d(TAG, "PlayerMusicActivity - 서비스 시작")
        val binder = service as MusicService.MusicBinder
        musicService = binder.currentService()
        musicService!!.createMusicPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(TAG, "PlayerMusicActivity - 서비스 종료")
        musicService = null
    }
}
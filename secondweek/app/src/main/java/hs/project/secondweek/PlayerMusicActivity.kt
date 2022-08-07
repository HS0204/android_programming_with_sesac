package hs.project.secondweek

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.databinding.ActivityPlayermusicBinding

class PlayerMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPlayermusicBinding.inflate(layoutInflater) }

    companion object {
        const val TAG: String = "MYLOG"

        private lateinit var musicList: ArrayList<MusicInfoData>
        private var musicPosition: Int = 0

        lateinit var player: MediaPlayer
        var isPlaying: Boolean = true
        var isShuffling: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeData()
        initializeLayout(isShuffling)
        createMusicPlayer()

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

        // 제목 흐르게 세팅
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        // 음악 리스트 activity 인텐트
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
            player = MediaPlayer()

            player.reset()
            player.setDataSource(musicList[musicPosition].path)
            player.prepare()
            player.start()

            isPlaying = true
            binding.controlIcon.setImageResource(R.drawable.icon_pause)

            Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 $player 생성, 재생 음악 ${musicList[musicPosition].title}")
        }catch (e:Exception){return}
    }

    private fun playMusic() {
        binding.controlIcon.setImageResource(R.drawable.icon_pause)
        isPlaying = true
        player.start()
    }

    private fun pauseMusic() {
        binding.controlIcon.setImageResource(R.drawable.icon_playing)
        isPlaying = false
        player.pause()
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
            killMusicPlayer()
            initializeLayout(isShuffling)
            createMusicPlayer()
        }
        else{
            Log.d(TAG, "PlayerMusicActivity - Previous 버튼 클릭")
            setMusicPosition(increment = false)
            killMusicPlayer()
            initializeLayout(isShuffling)
            createMusicPlayer()
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

    private fun killMusicPlayer() {
        Log.d(TAG, "PlayerMusicActivity - 음악 플레이어 $player 삭제")
        player.stop()
        player.release()
    }

    override fun onBackPressed() {
        Log.d(TAG, "PlayerMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}
package hs.project.secondweek

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Adapter.*
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Service.MusicService
import hs.project.secondweek.databinding.ActivityListmusicBinding
import java.io.File

// 서비스
private var player: MusicService? = null
var serviceBound = false

class ListMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListmusicBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var dieAdapter: MusicListAdapter

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
            Log.d("MYLOG", "ListMusicActivity -> 하단 음악 바 클릭")
            if (mediaPlayer != null) {
                val intent = Intent(this, PlayerMusicActivity::class.java)
                startActivity(intent)
            } else {
                MainActivity
            }

        }
    }

    override fun onStart() {
        Log.d(TAG, "ListMusicActivity - onStart() 호출")
        super.onStart()
        initializeData()
    }

    override fun onResume() {
        Log.d(TAG, "ListMusicActivity - onResume() 호출")
        super.onResume()
        initializeMusicBar()
        initializeLayout() // !!!!!어댑터O -> 변경할 것
    }

    override fun onPause() {
        Log.d(TAG, "ListMusicActivity - onPause() 호출")
        super.onPause()
        callStateListener()
    }

    override fun onStop() {
        Log.d(TAG, "ListMusicActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "ListMusicActivity - onRestart() 호출")
        super.onRestart()
        callStateListener()
    }

    override fun onDestroy() {
        Log.d(TAG, "ListMusicActivity - onDestroy() 호출")
        super.onDestroy()
    }

    private fun initializeData() {
        Log.d(TAG, "ListMusicActivity - 데이터 초기화")
        myListTrack = getMusic()
    }

    private fun initializeLayout() {
        Log.d(TAG, "ListMusicActivity - 레이아웃 초기화")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.musicListSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        dieAdapter = MusicListAdapter(this, myListTrack)
        recyclerView.adapter = dieAdapter

    }

    private fun initializeMusicBar() {
        Log.d(TAG, "ListMusicActivity - 뮤직 재생 하단 바 초기화")
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
        else{
            Play?.setImageResource(R.drawable.icon_playing)

            /**
             * 액티비티가 생성되고 나서 딱 한 번만 가능한데, 사용자가 버튼을 누를 때마다 업데이트가 되도록 어떻게 할 수 있을까?
             * 커서를 이용해볼까?
             * **/
            if (!mediaPlayer!!.isPlaying) {
                binding.musicList.setImageResource(R.drawable.icon_music_list)
            }
            else {
                Glide.with(this).load(myListTrack[musicPosition].artUri).apply(
                    RequestOptions()
                        .placeholder(R.drawable.album_art).fitCenter())
                    .into(binding.musicList)
            }
        }

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
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
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
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriC = Uri.withAppendedPath(uri, albumIdC).toString()

                    val music = MusicInfoData(
                        id = idC,
                        title = titleC,
                        album = albumC,
                        artist = artistC,
                        path = pathC,
                        duration = durationC,
                        artUri = artUriC
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

    private fun pauseMusic() {
        Play?.setImageResource(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }

    private fun playMusic() {
        Play?.setImageResource(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }

    private fun callStateListener() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyManager?.registerTelephonyCallback(
                mainExecutor,
                object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                    override fun onCallStateChanged(state: Int) {
                        when (state) {
                            TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING ->
                                if (mediaPlayer!!.isPlaying) {
                                    ongoingCall = true
                                    pauseMusic()
                                }
                            TelephonyManager.CALL_STATE_IDLE ->
                                if (mediaPlayer != null) {
                                    if (ongoingCall) {
                                        ongoingCall = false
                                        playMusic()
                                    }
                                }
                        }
                    }

                }
            )
        }
        else {
            phoneStateListener = object : PhoneStateListener() {
                override fun onCallStateChanged(state: Int, incomingNumber: String) {

                    when (state) {
                        TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING ->
                            if (mediaPlayer!!.isPlaying) {
                                ongoingCall = true
                                pauseMusic()
                            }
                        TelephonyManager.CALL_STATE_IDLE ->
                            if (mediaPlayer != null) {
                                if (ongoingCall) {
                                    ongoingCall = false
                                    playMusic()
                                }
                            }
                    }

                }
            }

            telephonyManager!!.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    override fun onBackPressed() {
        Log.d(TAG, "ListMusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }
}
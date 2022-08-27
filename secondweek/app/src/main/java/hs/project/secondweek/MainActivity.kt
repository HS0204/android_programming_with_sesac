package hs.project.secondweek

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Fragment.*
import hs.project.secondweek.PlayerMusicActivity.Companion.musicService
import hs.project.secondweek.databinding.ActivityMainBinding
import java.io.File

var mediaPlayer: MediaPlayer? = null

var changeTextTitle = "곡 제목"
var changeTextArtist = "가수"
lateinit var changeCover: ByteArray

lateinit var musicListAdapter: MusicListAdapter
var selectedMusic: MusicInfoData? = null

var localMusicList = ArrayList<MusicInfoData>()
var customMusicList = ArrayList<MusicInfoData>()

var currentSongIndex = 0
var musicPosition = 0

var recyclerViewBottomPadding = 0

val seekBarHandler = Handler()

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, Owner {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var homeFragment: HomeFragment
    private lateinit var music4UFragment: Music4UFragment
    private lateinit var myMusicFragment: MyMusicFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var alwaysFragment: AlwaysFragment

    var ongoingCall = false
    var phoneStateListener: PhoneStateListener? = null
    var telephonyManager: TelephonyManager? = null

    val PERMISSION_CODE = 100

    companion object {
        const val TAG: String = "MYLOG"

        var TitleN: TextView? = null
        var ArtistN: TextView? = null
        var PlayN: ImageView? = null
        var List: ImageView? = null

        var miniPlayer: ConstraintLayout? = null
        var bottomNav: BottomNavigationView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity - onCreate() 호출")

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val miniPlayerHeight = binding.musicPlayerSection.height
                val bottomNavHeight = binding.bottomNavigation.height

                recyclerViewBottomPadding = miniPlayerHeight + bottomNavHeight

                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        setContentView(binding.root)

        TitleN = binding.musicTitle
        ArtistN = binding.musicSinger
        PlayN = binding.musicControl
        List = binding.musicList
        miniPlayer = binding.musicPlayerSection
        bottomNav = binding.bottomNavigation

        requestPermission()
        callStateListener()

        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer()

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(binding.viewSection.id, homeFragment).commit()

        binding.bottomNavigation.selectedItemId = R.id.menu_home
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        PlayN?.setOnClickListener {
            Log.d(TAG, "MainActivity -> 음악 컨트롤 버튼 클릭")
            if (mediaPlayer == null) {
                Toast.makeText(this, "내 음악 -> 로컬 음악에서 리스트를 채워주세요", Toast.LENGTH_SHORT).show().toString()
            } else if (mediaPlayer!!.isPlaying)
                pauseMusic()
            else if (mediaPlayer != null)
                playMusic()
        }

        binding.musicPlayerSection.setOnClickListener {
            Log.d("MYLOG", "MainActivity -> 하단 음악 바를 클릭")
            if (customMusicList.size != 0) {
                val intent = Intent(this, PlayerMusicActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "내 음악 -> 로컬 음악에서 리스트를 채워주세요", Toast.LENGTH_SHORT).show()
                    .toString()
            }
        }

        binding.musicList.setOnClickListener {
            val intent = Intent(this, CustomListMusicActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() 호출")
        initializeData()
        initializeLayout()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() 호출")
        initializeSeekBar()
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
        seekBarHandler.removeMessages(0)
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "저장소 접근 허용", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "저장소 접근 불가", Toast.LENGTH_SHORT).show()
                }

                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "핸드폰 상태 접근 허용", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "핸드폰 상태 접근 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initializeData() {
        Log.d(TAG, "MainActivity - 데이터 초기화")
        localMusicList = getMusic()
    }

    private fun initializeSeekBar() {
        val seekBar = binding.musicBar
        seekBar.max = mediaPlayer!!.duration
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) mediaPlayer!!.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        seekBarHandler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    seekBarHandler.postDelayed(this, 1000)

                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        }, 0)
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
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                            .toString()
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

                    if (file.exists()) {
                        tempList.add(music)
                    }
                } while (cursor.moveToNext())
            cursor.close()
        }
        return tempList
    }

    private fun initializeLayout() {
        Log.d(TAG, "MainActivity - 레이아웃 초기화")
        binding.musicTitle.isSingleLine = true
        binding.musicTitle.isSelected = true
        binding.musicTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        binding.musicSinger.isSingleLine = true
        binding.musicSinger.isSelected = true
        binding.musicSinger.ellipsize = TextUtils.TruncateAt.MARQUEE
    }

    private fun pauseMusic() {
        PlayN?.setImageResource(R.drawable.icon_playing)
        musicService!!.showNotification(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }

    private fun playMusic() {
        PlayN?.setImageResource(R.drawable.icon_pause)
        musicService!!.showNotification(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }

    private fun callStateListener() {
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d(TAG, "callStateListener 버전 이상 동작")
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
        } else {
            Log.d(TAG, "callStateListener 버전 이하 동작")
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity - 홈 클릭")
                homeFragment = HomeFragment.newInstance()
                transaction.replace(binding.viewSection.id, homeFragment, "home")
                    .addToBackStack(null)
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MainActivity - 뮤직4U 클릭")
                music4UFragment = Music4UFragment.newInstance()
                transaction.replace(binding.viewSection.id, music4UFragment, "music4U")
                    .addToBackStack(null)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MainActivity - 내 음악 클릭")
                myMusicFragment = MyMusicFragment.newInstance()
                transaction.replace(binding.viewSection.id, myMusicFragment, "myMusic")
                    .addToBackStack(null)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "MainActivity - 탐색 클릭")
                searchingFragment = SearchingFragment.newInstance()
                transaction.replace(binding.viewSection.id, searchingFragment, "searching")
                    .addToBackStack(null)
            }
            R.id.menu_always -> {
                Log.d(TAG, "MainActivity - 24/7 클릭")
                alwaysFragment = AlwaysFragment.newInstance()
                transaction.replace(binding.viewSection.id, alwaysFragment, "always")
                    .addToBackStack(null)
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
        return true
    }

    private fun updateBottomMenu(navigation: BottomNavigationView) {
        Log.d(TAG, "MainActivity - updateBottomMenu() 호출")
        val homeTag: Fragment? = supportFragmentManager.findFragmentByTag("home")
        val music4UTag: Fragment? = supportFragmentManager.findFragmentByTag("music4U")
        val myMusicTag: Fragment? = supportFragmentManager.findFragmentByTag("myMusic")
        val searchingTag: Fragment? = supportFragmentManager.findFragmentByTag("searching")
        val alwaysTag: Fragment? = supportFragmentManager.findFragmentByTag("always")

        if (homeTag != null && homeTag.isVisible) {
            navigation.menu.findItem(R.id.menu_home).isChecked = true
        }
        if (music4UTag != null && music4UTag.isVisible) {
            navigation.menu.findItem(R.id.menu_music4U).isChecked = true
        }
        if (myMusicTag != null && myMusicTag.isVisible) {
            navigation.menu.findItem(R.id.menu_my_music).isChecked = true
        }
        if (searchingTag != null && searchingTag.isVisible) {
            navigation.menu.findItem(R.id.menu_searching).isChecked = true
        }
        if (alwaysTag != null && alwaysTag.isVisible) {
            navigation.menu.findItem(R.id.menu_always).isChecked = true
        }
    }

    override fun onBackPressed() {
        Log.d(TAG, "MainActivity - onBackPressed() 호출")
        Log.d(TAG, "현재 스택의 수는 ${supportFragmentManager.backStackEntryCount}")
        super.onBackPressed()

        updateBottomMenu(binding.bottomNavigation)
        overridePendingTransition(0, 0)

    }

    override fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.viewSection.id, fragment).addToBackStack(null).commit()
    }

}
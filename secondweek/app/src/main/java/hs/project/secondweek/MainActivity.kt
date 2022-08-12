package hs.project.secondweek

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.Adapter.mediaPlayer
import hs.project.secondweek.Fragment.*
import hs.project.secondweek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var homeFragment: HomeFragment
    private lateinit var music4UFragment: Music4UFragment
    private lateinit var myMusicFragment: MyMusicFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var alwaysFragment: AlwaysFragment

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

        requestPermission()

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(binding.viewSection.id, homeFragment).commit()

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
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() 호출")

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
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity - 홈 클릭")
                homeFragment = HomeFragment.newInstance()
                transaction.replace(binding.viewSection.id, homeFragment, "home")
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MainActivity - 뮤직4U 클릭")
                music4UFragment = Music4UFragment.newInstance()
                transaction.replace(binding.viewSection.id, music4UFragment, "music4U")
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MainActivity - 내 음악 클릭")
                myMusicFragment = MyMusicFragment.newInstance()
                transaction.replace(binding.viewSection.id, myMusicFragment, "myMusic")
            }
            R.id.menu_searching -> {
                Log.d(TAG, "MainActivity - 탐색 클릭")
                searchingFragment = SearchingFragment.newInstance()
                transaction.replace(binding.viewSection.id, searchingFragment, "searching")
            }
            R.id.menu_always -> {
                Log.d(TAG, "MainActivity - 24/7 클릭")
                alwaysFragment = AlwaysFragment.newInstance()
                transaction.replace(binding.viewSection.id, alwaysFragment, "always")
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

        if(homeTag != null && homeTag.isVisible) {navigation.menu.findItem(R.id.menu_home).isChecked = true }
        if(music4UTag != null && music4UTag.isVisible) {navigation.menu.findItem(R.id.menu_music4U).isChecked = true }
        if(myMusicTag != null && myMusicTag.isVisible) {navigation.menu.findItem(R.id.menu_my_music).isChecked = true }
        if(searchingTag != null && searchingTag.isVisible) {navigation.menu.findItem(R.id.menu_searching).isChecked = true }
        if(alwaysTag != null && alwaysTag.isVisible) {navigation.menu.findItem(R.id.menu_always).isChecked = true }
    }

    override fun onBackPressed() {
        Log.d(TAG, "MainActivity - onBackPressed() 호출")
        Log.d(TAG, "현재 스택의 수는 ${supportFragmentManager.backStackEntryCount}")
        super.onBackPressed()

        updateBottomMenu(binding.bottomNavigation)
        overridePendingTransition(0, 0)

    }

}
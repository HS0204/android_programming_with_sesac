package hs.project.secondweek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var homeFragment: HomeFragment
    private lateinit var music4UFragment: Music4UFragment
    private lateinit var myMusicFragment: MyMusicFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var alwaysFragment: AlwaysFragment

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity - onCreate() 호출")
        setContentView(binding.root)

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.view_section, homeFragment).commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() 호출")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() 호출")
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

    private val onBottomNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity - 홈 클릭")
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.view_section, homeFragment).commit()
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MainActivity - 뮤직4U 클릭")
                music4UFragment = Music4UFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.view_section, music4UFragment).commit()
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MainActivity - 내 음악 클릭")
                myMusicFragment = MyMusicFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.view_section, myMusicFragment).commit()
            }
            R.id.menu_searching -> {
                Log.d(TAG, "MainActivity - 탐색 클릭")
                searchingFragment = SearchingFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.view_section, searchingFragment).commit()
            }
            R.id.menu_always -> {
                Log.d(TAG, "MainActivity - 24/7 클릭")
                alwaysFragment = AlwaysFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.view_section, alwaysFragment).commit()
            }
        }
        true
    }

}
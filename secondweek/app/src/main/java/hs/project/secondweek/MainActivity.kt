package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import hs.project.secondweek.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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
        supportFragmentManager.beginTransaction().add(binding.viewSection.id, homeFragment).commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        binding.musicPlayerSection.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when(item.itemId){
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

        if(supportFragmentManager.backStackEntryCount == 0){
            onStop()
            onDestroy()
            System.runFinalization()
            exitProcess(0)
        }

        super.onBackPressed()
        updateBottomMenu(binding.bottomNavigation)
    }

}
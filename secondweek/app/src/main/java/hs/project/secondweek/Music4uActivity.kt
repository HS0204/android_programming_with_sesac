package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.databinding.ActivityMainBinding
import hs.project.secondweek.databinding.ActivityMusic4uBinding

class Music4uActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val music4uBinding by lazy { ActivityMusic4uBinding.inflate(layoutInflater) }

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Music4uActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(music4uBinding.root)

        music4uBinding.bottomNavigation.selectedItemId = R.id.menu_music4U
        music4uBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        Log.d(TAG, "Music4uActivity - onStart() 호출")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "Music4uActivity - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "Music4uActivity - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "Music4uActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "Music4uActivity - onRestart() 호출")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "Music4uActivity - onDestroy() 호출")
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "Music4uActivity - 홈 클릭")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "Music4uActivity - 뮤직4U 클릭")
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "Music4uActivity - 내 음악 클릭")
                val intent = Intent(applicationContext, MymusicActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "Music4uActivity - 탐색 클릭")
                val intent = Intent(applicationContext, SearchingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_always -> {
                Log.d(TAG, "Music4uActivity - 24/7 클릭")
                val intent = Intent(applicationContext, AlwaysActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "Music4uActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }

}
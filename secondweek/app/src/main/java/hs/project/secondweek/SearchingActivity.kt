package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.databinding.ActivityMainBinding
import hs.project.secondweek.databinding.ActivitySearchingBinding

class SearchingActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val searchingBinding by lazy { ActivitySearchingBinding.inflate(layoutInflater) }

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchingActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(searchingBinding.root)

        searchingBinding.bottomNavigation.selectedItemId = R.id.menu_searching
        searchingBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        Log.d(TAG, "SearchingActivity - onStart() 호출")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "SearchingActivity - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "SearchingActivity - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "SearchingActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "SearchingActivity - onRestart() 호출")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "SearchingActivity - onDestroy() 호출")
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "SearchingActivity - 홈 클릭")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "SearchingActivity - 뮤직4U 클릭")
                val intent = Intent(applicationContext, Music4uActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "SearchingActivity - 내 음악 클릭")
                val intent = Intent(applicationContext, MymusicActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "SearchingActivity - 탐색 클릭")

            }
            R.id.menu_always -> {
                Log.d(TAG, "SearchingActivity - 24/7 클릭")
                val intent = Intent(applicationContext, AlwaysActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "SearchingActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }

}
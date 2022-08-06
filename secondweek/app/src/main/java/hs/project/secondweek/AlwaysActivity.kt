package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.databinding.ActivityAlwaysBinding

class AlwaysActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val alwaysBinding by lazy { ActivityAlwaysBinding.inflate(layoutInflater) }

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "AlwaysActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(alwaysBinding.root)

        alwaysBinding.bottomNavigation.selectedItemId = R.id.menu_always
        alwaysBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        Log.d(TAG, "AlwaysActivity - onStart() 호출")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "AlwaysActivity - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "AlwaysActivity - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "AlwaysActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "AlwaysActivity - onRestart() 호출")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "AlwaysActivity - onDestroy() 호출")
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "AlwaysActivity - 홈 클릭")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_music4U -> {
                Log.d(TAG, "AlwaysActivity - 뮤직4U 클릭")
                val intent = Intent(applicationContext, Music4uActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "AlwaysActivity - 내 음악 클릭")
                val intent = Intent(applicationContext, MymusicActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_searching -> {
                Log.d(TAG, "AlwaysActivity - 탐색 클릭")
                val intent = Intent(applicationContext, SearchingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_always -> {
                Log.d(TAG, "AlwaysActivity - 24/7 클릭")

            }
        }

        return true
    }


    override fun onBackPressed() {
        Log.d(TAG, "AlwaysActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }
}
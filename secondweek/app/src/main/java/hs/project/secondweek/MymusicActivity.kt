package hs.project.secondweek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import hs.project.secondweek.databinding.ActivityMymusicBinding

class MymusicActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val mymusicBinding by lazy {ActivityMymusicBinding.inflate(layoutInflater)}

    companion object {
        const val TAG: String = "MYLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MymusicActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(mymusicBinding.root)

        mymusicBinding.bottomNavigation.selectedItemId = R.id.menu_my_music
        mymusicBinding.bottomNavigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        Log.d(TAG, "MymusicActivity - onStart() 호출")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "MymusicActivity - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "MymusicActivity - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "MymusicActivity - onStop() 호출")
        super.onStop()
    }

    override fun onRestart() {
        Log.d(TAG, "MymusicActivity - onRestart() 호출")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "MymusicActivity - onDestroy() 호출")
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                Log.d(TAG, "MymusicActivity - 홈 클릭")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)

            }
            R.id.menu_music4U -> {
                Log.d(TAG, "MymusicActivity - 뮤직4U 클릭")
                val intent = Intent(this, Music4uActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_my_music -> {
                Log.d(TAG, "MymusicActivity - 내 음악 클릭")

            }
            R.id.menu_searching -> {
                Log.d(TAG, "MymusicActivity - 탐색 클릭")
                val intent = Intent(this, SearchingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            R.id.menu_always -> {
                Log.d(TAG, "MymusicActivity - 24/7 클릭")
                val intent = Intent(this, AlwaysActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
        }

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "MymusicActivity - onBackPressed() 호출")
        super.onBackPressed()
        overridePendingTransition(0,0)

    }

}
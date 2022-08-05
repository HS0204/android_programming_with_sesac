package hs.project.secondweek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MusicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
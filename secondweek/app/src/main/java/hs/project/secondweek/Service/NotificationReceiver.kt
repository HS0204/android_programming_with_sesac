package hs.project.secondweek.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import hs.project.secondweek.CustomListMusicActivity
import hs.project.secondweek.MainActivity
import hs.project.secondweek.PlayerMusicActivity.Companion.musicService
import hs.project.secondweek.R
import hs.project.secondweek.mediaPlayer

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PREVIOUS -> Toast.makeText(context, "이전 곡 클릭", Toast.LENGTH_SHORT).show()
            ApplicationClass.PLAY -> if (mediaPlayer!!.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.NEXT -> Toast.makeText(context, "다음 곡 클릭", Toast.LENGTH_SHORT).show()
            //ApplicationClass.EXIT -> {
            //    musicService!!.stopForeground(true)
            //    musicService = null
            //    exitProcess(1)
            //}
        }
    }

    private fun playMusic() {
        musicService!!.showNotification(R.drawable.icon_pause)
        MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)
        CustomListMusicActivity.playBtnCustom?.setImageResource(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        musicService!!.showNotification(R.drawable.icon_playing)
        MainActivity.PlayN?.setImageResource(R.drawable.icon_playing)
        CustomListMusicActivity.playBtnCustom?.setImageResource(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }
}
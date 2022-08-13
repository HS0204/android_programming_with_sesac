package hs.project.secondweek.Service

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import hs.project.secondweek.*

class MusicService: Service() {
    private var musicBinder = MusicBinder()
    private lateinit var mediaSession: MediaSessionCompat

    var player: MediaPlayer?= null

    private val TAG: String = "MYLOG"

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "MusicService - onBind() 호출")
        mediaSession = MediaSessionCompat(baseContext, "music")

        return musicBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "MusicService - onUnbind() 호출")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "MusicService - onDestroy() 호출")
        super.onDestroy()
        releaseMusicPlayer()
    }

    inner class MusicBinder: Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }



    private fun releaseMusicPlayer() {
        Log.d(PlayerMusicActivity.TAG, "MusicService - 음악 플레이어 ${PlayerMusicActivity.musicService!!.player} 삭제")
        PlayerMusicActivity.musicService!!.player!!.release()
    }

    fun showNotification() {
        Log.d(TAG, "MusicService - showNotification() 호출")
        val notification = androidx.core.app.NotificationCompat.Builder(baseContext,
            ApplicationClass.CHANNEL_ID
        )
            .setContentTitle(localMusicList[musicPosition].title)
            .setContentText(localMusicList[musicPosition].artist)
            .setSmallIcon(R.drawable.icon_music_list)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.album_art))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.icon_music_previous, "Previous", null)
            .addAction(R.drawable.icon_playing, "Play", null)
            .addAction(R.drawable.icon_music_next, "Next", null)
            .build()

       startForeground(13,notification)
    }
}
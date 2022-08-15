package hs.project.secondweek.Service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import hs.project.secondweek.Data.getImage
import hs.project.secondweek.R
import hs.project.secondweek.customMusicList
import hs.project.secondweek.musicPosition

class MusicService: Service() {
    private var musicBinder = MusicBinder()
    private lateinit var mediaSession: MediaSessionCompat

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
    }

    inner class MusicBinder: Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }


    fun showNotification(controlBtn: Int) {
        Log.d(TAG, "MusicService - showNotification() 호출")

        val prevIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(baseContext, 0, prevIntent, PendingIntent.FLAG_MUTABLE)

        val playIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(baseContext, 0, playIntent, PendingIntent.FLAG_MUTABLE)

        val nextIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(baseContext, 0, nextIntent, PendingIntent.FLAG_MUTABLE)

        //val exitIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
        //val exitPendingIntent = PendingIntent.getBroadcast(baseContext, 0, exitIntent, PendingIntent.FLAG_MUTABLE)

        val musicImage = getImage(customMusicList[musicPosition].path)
        val image = if (musicImage != null) {
            BitmapFactory.decodeByteArray(musicImage, 0, musicImage.size)
        }else {
            BitmapFactory.decodeResource(resources, R.drawable.album_art)
        }

        val notification = androidx.core.app.NotificationCompat.Builder(baseContext,
            ApplicationClass.CHANNEL_ID
        )
            .setContentTitle(customMusicList[musicPosition].title)
            .setContentText(customMusicList[musicPosition].artist)
            .setSmallIcon(R.drawable.icon_music_list)
            .setLargeIcon(image)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.icon_music_previous, "Previous", prevPendingIntent)
            .addAction(controlBtn, "Play", playPendingIntent)
            .addAction(R.drawable.icon_music_next, "Next", nextPendingIntent)
            //.addAction(R.drawable.ic_baseline_home_24, "Next", exitPendingIntent)
            .build()

       startForeground(13,notification)
    }
}
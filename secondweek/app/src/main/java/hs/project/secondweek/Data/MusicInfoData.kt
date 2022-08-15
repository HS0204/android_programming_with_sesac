package hs.project.secondweek.Data

import android.media.MediaMetadataRetriever
import hs.project.secondweek.customMusicList
import hs.project.secondweek.musicPosition
import java.util.concurrent.TimeUnit

data class MusicInfoData(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val artUri: String
)

fun formatDuration (duration: Long): String{
    val min = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val sec = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
            - min * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))

    return String.format("%02d:%02d", min, sec)
}

fun getImage (path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

fun setMusicPosition(increment: Boolean) {
    if (increment){
        if (customMusicList.size - 1 == musicPosition)
            musicPosition = 0
        else ++musicPosition
    }
    else{
        if (0 == musicPosition)
            musicPosition = customMusicList.size - 1
        else --musicPosition
    }
}
package hs.project.secondweek.Data

data class MusicInfoData(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String
)
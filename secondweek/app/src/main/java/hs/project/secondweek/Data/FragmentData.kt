package hs.project.secondweek.Data

// 홈

data class RecommendedMusicData(
    var image: Int,
    var txtTitle: String
)

data class NewMusicData(
    var image: Int,
    var txtTitle: String,
    var txtSinger: String
)

data class VideoMusicData(
    var image: Int,
    var txtTitle: String,
    var txtSinger: String
)

// 뮤직4U
data class Music4uData(
    val album: Int,
    val title: String,
    val artists: String
)

// 탐색
data class SearchingTagData(
    val text: String
)

data class SearchingEditorData(
    val img: Int,
    val title: String,
    val writer: String
)
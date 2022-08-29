package hs.project.secondweek.Data

data class MusicSimilarData(
    val Similar: Similar
)

data class Similar(
    val Info: List<TargetArtist>,
    val Results: List<SimilarArtist>
)

data class TargetArtist(
    val Name: String,
    val Type: String,
    val wTeaser: String,
    val wUrl: String,
    val yID: String,
    val yUrl: String
)

data class SimilarArtist(
    val Name: String,
    val Type: String,
    val wTeaser: String,
    val wUrl: String,
    val yID: String,
    val yUrl: String
)
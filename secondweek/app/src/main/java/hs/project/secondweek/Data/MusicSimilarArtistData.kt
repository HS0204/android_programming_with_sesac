package hs.project.secondweek.Data

data class MusicSimilarData(
    val Similar: Similar
)

data class Similar(
    val Info: List<SimilarArtist>,
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
    val yUrl: String,
    var imgUrl: String?
)

data class ArtistImgData(
    val documents: List<Document>,
    val meta: Meta
)

data class Document(
    val collection: String,
    val datetime: String,
    val display_sitename: String,
    val doc_url: String,
    val height: Int,
    val image_url: String,
    val thumbnail_url: String,
    val width: Int
)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)
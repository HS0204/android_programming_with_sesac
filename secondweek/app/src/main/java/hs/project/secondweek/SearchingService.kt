package hs.project.secondweek

import hs.project.secondweek.Data.ArtistImgData
import hs.project.secondweek.Data.MusicITunesData
import hs.project.secondweek.Data.MusicSimilarData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchingService {

    // https://itunes.apple.com/search?term={keyword}&limit={count}&entity={kind}
    @GET("search?")
    fun getMusic(
        @Query("term") keyword: String,
        @Query("limit") count: Int,
        @Query("entity") kind: String
    ): Call<MusicITunesData>

    // https://tastedive.com/api/similar?q={targetArtist}&info={info}&type={type}&limit={limit}
    @GET("similar?")
    fun searchSimilarArtist(
        @Query("q") targetArtist: String,
        @Query("info") info: Int,
        @Query("type") type: String,
        @Query("limit") limit: Int
    ): Call<MusicSimilarData>

    // https://dapi.kakao.com/v2/search/image?query={target}&page={page}&size={size}
    @GET("image?")
    fun searchArtistImg(
        @Header("Authorization") auth: String?,
        @Query("query") target: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ArtistImgData>

}
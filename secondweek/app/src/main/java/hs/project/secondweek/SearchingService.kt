package hs.project.secondweek

import hs.project.secondweek.Data.MusicITunesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchingService {

    @GET("search?")
    fun getMusic(
        @Query("term") keyword: String,
        @Query("limit") count: Int,
        @Query("entity") kind: String
    ): Call<MusicITunesData>

}
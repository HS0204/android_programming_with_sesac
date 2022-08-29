package hs.project.secondweek.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.BuildConfig
import hs.project.secondweek.Data.ArtistImgData
import hs.project.secondweek.Data.SimilarArtist
import hs.project.secondweek.R
import hs.project.secondweek.SearchingService
import hs.project.secondweek.databinding.LayoutMusicListBinding
import hs.project.secondweek.databinding.LayoutMusicListSearchedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicSearchArtistAdapter(
    private val context: Context,
    private val artistList: List<SimilarArtist>
) : RecyclerView.Adapter<MusicSearchArtistAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicSearchArtistAdapter.SearchArtistViewHolder {
        val view = LayoutMusicListSearchedBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchArtistViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MusicSearchArtistAdapter.SearchArtistViewHolder,
        position: Int
    ) {
        return holder.bindArtist(artistList[position])
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    inner class SearchArtistViewHolder(binding: LayoutMusicListSearchedBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image: ImageView = binding.musicListImg
        private val name: TextView = binding.musicListTitle
        private val genre: TextView = binding.musicListSinger
        private val favorite: ImageView = binding.musicListIcon

        fun bindArtist(info: SimilarArtist) {
            name.text = info.Name
            genre.text = info.wTeaser
            favorite.setImageResource(R.drawable.icon_favorite)
            searchingMusic(info.Name)
        }

        private fun searchingMusic(keyword: String) {
            val baseUrl = "https://dapi.kakao.com/v2/search/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service: SearchingService = retrofit.create(SearchingService::class.java)
            val searchingMusic = service.searchArtistImg(BuildConfig.KAKAO_IMAGE_API_AUTH, keyword, 1, 1)

            searchingMusic.enqueue(object: Callback<ArtistImgData> {
                override fun onResponse(
                    call: Call<ArtistImgData>,
                    response: Response<ArtistImgData>
                ) {
                    Log.d("Retrofit", "이미지 찾기 | 현재 키워드 : $keyword")
                    val body = response.body()

                    if (body != null) {
                        Log.d("Retrofit", "이미지 찾기 | 통신 성공")
                        Glide.with(context).load(body.documents[0].thumbnail_url)
                            .apply(RequestOptions().placeholder(R.drawable.album_art)).fitCenter()
                            .into(image)
                    }
                    else {
                        Log.d("Retrofit","곡 찾기 | 바디 null")
                    }
                }

                override fun onFailure(call: Call<ArtistImgData>, t: Throwable) {
                    Log.d("Retrofit", "이미지 찾기 | 통신 실패", t)
                }
            })
        }
    }
}
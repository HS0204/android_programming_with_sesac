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
            Glide.with(context).load(info.imgUrl)
                .apply(RequestOptions().placeholder(R.drawable.album_art)).fitCenter()
                .into(image)

        }
    }
}
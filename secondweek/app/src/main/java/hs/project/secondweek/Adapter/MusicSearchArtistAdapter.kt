package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hs.project.secondweek.Data.SimilarArtist
import hs.project.secondweek.R
import hs.project.secondweek.databinding.LayoutMusicListBinding

class MusicSearchArtistAdapter(
    private val context: Context,
    private val artistList: List<SimilarArtist>
) : RecyclerView.Adapter<MusicSearchArtistAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicSearchArtistAdapter.SearchArtistViewHolder {
        val view = LayoutMusicListBinding.inflate(LayoutInflater.from(context), parent, false)
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

    inner class SearchArtistViewHolder(binding: LayoutMusicListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image: ImageView = binding.musicListImg
        private val name: TextView = binding.musicListTitle
        private val genre: TextView = binding.musicListSinger
        private val favorite: TextView = binding.musicListTime

        fun bindArtist(info: SimilarArtist) {
            name.text = info.Name
            genre.text = info.wTeaser
            favorite.text = "하트 아이콘"
            Glide.with(context).load(R.drawable.album_art).fitCenter().into(image)
        }
    }
}
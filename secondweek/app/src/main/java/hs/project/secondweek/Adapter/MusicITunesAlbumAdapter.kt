package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.ITunesResult
import hs.project.secondweek.R
import hs.project.secondweek.databinding.LayoutAlbumListBinding
import hs.project.secondweek.databinding.LayoutMusicListBinding
import hs.project.secondweek.databinding.LayoutMusicListSearchedBinding

class MusicITunesAlbumAdapter(
    private val context: Context,
    private val musicList: List<ITunesResult>
) : RecyclerView.Adapter<MusicITunesAlbumAdapter.MusicITunesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicITunesViewHolder {
        val view = LayoutAlbumListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MusicITunesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicITunesViewHolder, position: Int) {
        return holder.bindMusic(musicList[position])
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    inner class MusicITunesViewHolder (binding: LayoutAlbumListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val setCover: ImageView = binding.musicListImg
        private val songTitle: TextView = binding.musicListTitle
        private val songArtist: TextView = binding.musicListSinger
        private val songRelease: TextView = binding.musicReleaseDate
        private val songGenre: TextView = binding.musicGenre

        fun bindMusic(musicInfo: ITunesResult) {
            songTitle.text = musicInfo.trackName
            songArtist.text = musicInfo.artistName
            songRelease.text = musicInfo.releaseDate
            songGenre.text = musicInfo.primaryGenreName

            Glide.with(context).load(musicInfo.artworkUrl100)
                .apply(RequestOptions().placeholder((R.drawable.album_art)).fitCenter())
                .into(setCover)
        }
    }
}
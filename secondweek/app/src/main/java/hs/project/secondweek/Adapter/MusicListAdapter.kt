package hs.project.secondweek.Adapter

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.formatDuration
import hs.project.secondweek.R
import hs.project.secondweek.databinding.LayoutMusicListBinding
import hs.project.secondweek.localMusicList
import hs.project.secondweek.mediaPlayer
import java.util.*

class MusicListAdapter(
    private val context: Context,
    private val dataList: ArrayList<MusicInfoData>,
    private val custom: Boolean
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    init {
        Log.d("MYLOG", "MyTrackAdapter -> 뮤직 리스트 초기화")
        localMusicList = dataList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicListAdapter.MusicListViewHolder {
        val view = LayoutMusicListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MusicListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicListAdapter.MusicListViewHolder, position: Int) {
        return holder.bindMusic(localMusicList[position])

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun removeData (position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData (fromPosition: Int, toPosition: Int) {
        Collections.swap(dataList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class MusicListViewHolder(binding: LayoutMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        private val option = binding.musicListOption

        private val setCover: ImageView = binding.musicListImg
        private val songTitle: TextView = binding.musicListTitle
        private val songArtist: TextView = binding.musicListSinger
        private val songTime: TextView = binding.musicListTime

        fun bindMusic(songInfo: MusicInfoData) {
            songTitle.text = songInfo.title
            songArtist.text = songInfo.artist
            songTime.text = formatDuration(localMusicList[position].duration)

            Glide.with(context).load(localMusicList[position].artUri)
                .apply(RequestOptions().placeholder((R.drawable.album_art)).fitCenter())
                .into(setCover)

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()


        }

    }

}
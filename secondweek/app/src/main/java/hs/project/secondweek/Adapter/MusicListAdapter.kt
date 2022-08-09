package hs.project.secondweek.Adapter

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.formatDuration
import hs.project.secondweek.ListMusicActivity
import hs.project.secondweek.MainActivity
import hs.project.secondweek.R
import hs.project.secondweek.databinding.LayoutMusicListBinding

var adapter: MusicListAdapter? = null
var mediaPlayer: MediaPlayer? = null
var songe: MusicInfoData? = null
var currentSongIndex = 0

var changeTextTitle = "곡 제목"
var changeTextArtist = "가수"
lateinit var changeCover: ByteArray

var myListTrack = ArrayList<MusicInfoData>()
var musicPosition = 0

class MusicListAdapter(
    private val context: Context,
    private val dataList: ArrayList<MusicInfoData>
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    init {
        Log.d("MYLOG", "MyTrackAdapter -> 뮤직 리스트 초기화")
        myListTrack = dataList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicListAdapter.MusicListViewHolder {
        val view = LayoutMusicListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MusicListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicListAdapter.MusicListViewHolder, position: Int) {
        return holder.bindMusic(myListTrack[position])

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MusicListViewHolder(binding: LayoutMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root

        private val setCover: ImageView = binding.musicListImg
        private val songTitle: TextView = binding.musicListTitle
        private val songArtist: TextView = binding.musicListSinger
        private val songTime: TextView = binding.musicListTime

        fun bindMusic(songInfo: MusicInfoData) {
            songTitle.text = songInfo.title
            songArtist.text = songInfo.artist
            songTime.text = formatDuration(myListTrack[position].duration)

            Glide.with(context).load(myListTrack[position].artUri)
                .apply(RequestOptions().placeholder((R.drawable.album_art1)).fitCenter())
                .into(setCover)

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()

            root.setOnClickListener {
                Log.d("MYLOG", "MyTrackAdapter -> 뮤직 리스트에서 음악 재생")
                Log.d("MYLOG", "음악 플레이어 $mediaPlayer | 현재 곡 ${myListTrack[position].title}")

                musicPosition = position

                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(myListTrack[position].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

                changeTextTitle = songTitle.text.toString()
                changeTextArtist = songArtist.text.toString()

                MainActivity.TitleN?.text = changeTextTitle
                MainActivity.ArtistN?.text = changeTextArtist
                MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)

                ListMusicActivity.Title?.text = changeTextTitle
                ListMusicActivity.Artist?.text = changeTextArtist
                ListMusicActivity.Play?.setImageResource(R.drawable.icon_pause)

                songe = songInfo
            }

        }

    }


}
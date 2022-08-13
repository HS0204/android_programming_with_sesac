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
import hs.project.secondweek.*
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.formatDuration
import hs.project.secondweek.databinding.LayoutMusicListBinding

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

            root.setOnClickListener {
                if (custom) {
                    Log.d("MYLOG", "MyTrackAdapter -> 뮤직 리스트에서 음악 재생")
                    Log.d("MYLOG", "음악 플레이어 $mediaPlayer | 현재 곡 ${localMusicList[position].title}")

                    musicPosition = position

                    mediaPlayer!!.reset()
                    mediaPlayer!!.setDataSource(localMusicList[position].path)
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

                    CustomListMusicActivity.titleCustom?.text = changeTextTitle
                    CustomListMusicActivity.artistCustom?.text = changeTextArtist
                    CustomListMusicActivity.playBtnCustom?.setImageResource(R.drawable.icon_pause)

                    songe = songInfo
                }
                else {
                    Log.d("MYLOG", "${localMusicList[position].title}을 customMusicList에 추가")

                    val music = MusicInfoData(
                        id = localMusicList[position].id,
                        title = localMusicList[position].title,
                        album = localMusicList[position].album,
                        artist = localMusicList[position].artist,
                        path = localMusicList[position].path,
                        duration = localMusicList[position].duration,
                        artUri = localMusicList[position].artUri
                    )

                    customMusicList.add(music)
                }

            }

            option.setOnClickListener {

            }

        }

    }


}
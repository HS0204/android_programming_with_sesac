package hs.project.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.VideoMusicData

class VideoMusicAdapter(private val dataList : ArrayList<VideoMusicData>) :
    RecyclerView.Adapter<VideoMusicAdapter.VideoMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoMusicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_video_music, parent, false)
        return VideoMusicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VideoMusicViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.img.setImageResource(currentItem.image)
        holder.musicTitle.text = currentItem.txtTitle
        holder.musicSinger.text = currentItem.txtSinger
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class VideoMusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img : ImageView = itemView.findViewById(R.id.video_music_image)
        val musicTitle : TextView = itemView.findViewById(R.id.video_music_title)
        val musicSinger : TextView = itemView.findViewById(R.id.video_music_singer)
    }
}
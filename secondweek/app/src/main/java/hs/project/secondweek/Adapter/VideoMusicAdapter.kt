package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.VideoMusicData
import hs.project.secondweek.databinding.LayoutVideoMusicBinding

class VideoMusicAdapter(private val context : Context, private val dataList : ArrayList<VideoMusicData>) :
    RecyclerView.Adapter<VideoMusicAdapter.VideoMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoMusicViewHolder {
        return VideoMusicViewHolder(LayoutVideoMusicBinding.inflate(LayoutInflater.from(context), parent, false))
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

    class VideoMusicViewHolder(binding: LayoutVideoMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.videoMusicImage
        val musicTitle : TextView = binding.videoMusicTitle
        val musicSinger : TextView = binding.videoMusicSinger
    }
}
package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.databinding.LayoutMusicListBinding

class MusicListAdapter(private val context: Context, private val dataList : ArrayList<MusicInfoData>) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListAdapter.MusicListViewHolder {
        return MusicListViewHolder(LayoutMusicListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicListAdapter.MusicListViewHolder, position: Int) {

        holder.musicTitle.text = dataList[position].title
        holder.musicSinger.text = dataList[position].artist
        holder.musicTime.text = dataList[position].duration.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MusicListViewHolder(binding: LayoutMusicListBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.musicListImg
        val musicTitle : TextView = binding.musicListTitle
        val musicSinger : TextView = binding.musicListSinger
        val musicTime : TextView = binding.musicListTime
    }
}
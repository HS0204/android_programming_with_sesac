package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.MusicListData
import hs.project.secondweek.Data.NewMusicData
import hs.project.secondweek.databinding.LayoutMusicListBinding
import org.w3c.dom.Text

class MusicListAdapter(private val context: Context, private val dataList : ArrayList<MusicListData>) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListAdapter.MusicListViewHolder {
        return MusicListViewHolder(LayoutMusicListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicListAdapter.MusicListViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.img.setImageResource(currentItem.image)
        holder.musicTitle.text = currentItem.txtTitle
        holder.musicSinger.text = currentItem.textSinger
        holder.musictime.text = currentItem.textTime
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MusicListViewHolder(binding: LayoutMusicListBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.musicListImg
        val musicTitle : TextView = binding.musicListTitle
        val musicSinger : TextView = binding.musicListSinger
        val musictime : TextView = binding.musicListTime
    }
}
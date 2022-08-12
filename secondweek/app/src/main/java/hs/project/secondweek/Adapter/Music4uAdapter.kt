package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.Music4uData
import hs.project.secondweek.databinding.LayoutMusic4uBinding

class Music4uAdapter(
    private val context: Context,
    private val dataList: ArrayList<Music4uData>
) :
    RecyclerView.Adapter<Music4uAdapter.Music4UViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Music4UViewHolder {
        val view = LayoutMusic4uBinding.inflate(LayoutInflater.from(context), parent, false)
        return Music4UViewHolder(view)
    }

    override fun onBindViewHolder(holder: Music4UViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.albumArt.setImageResource(currentItem.album)
        holder.title.text = currentItem.title
        holder.singers.text = currentItem.artists
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class Music4UViewHolder(binding: LayoutMusic4uBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val albumArt: ImageView = binding.albumArt
        val title: TextView = binding.title
        val singers: TextView = binding.singers
    }
}
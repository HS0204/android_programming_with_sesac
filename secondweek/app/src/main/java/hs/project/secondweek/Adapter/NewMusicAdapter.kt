package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.NewMusicData
import hs.project.secondweek.databinding.LayoutNewMusicBinding

class NewMusicAdapter(private val context: Context, private val dataList : ArrayList<NewMusicData>) :
    RecyclerView.Adapter<NewMusicAdapter.NewMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMusicViewHolder {
        return NewMusicViewHolder(LayoutNewMusicBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: NewMusicViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.img.setImageResource(currentItem.image)
        holder.musicTitle.text = currentItem.txtTitle
        holder.musicSinger.text = currentItem.txtSinger

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class NewMusicViewHolder(binding: LayoutNewMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.newMusicImage
        val musicTitle : TextView = binding.newMusicTitle
        val musicSinger : TextView = binding.newMusicSinger
    }
}
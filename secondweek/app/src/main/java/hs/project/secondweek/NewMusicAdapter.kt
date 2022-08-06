package hs.project.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.NewMusicData

class NewMusicAdapter(private val dataList : ArrayList<NewMusicData>) :
    RecyclerView.Adapter<NewMusicAdapter.NewMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMusicAdapter.NewMusicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_new_music, parent, false)
        return NewMusicAdapter.NewMusicViewHolder(itemView)
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

    class NewMusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img : ImageView = itemView.findViewById(R.id.new_music_image)
        val musicTitle : TextView = itemView.findViewById(R.id.new_music_title)
        val musicSinger : TextView = itemView.findViewById(R.id.new_music_singer)
    }
}
package hs.project.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendedMusicAdapter(private val dataList : ArrayList<RecommendedMusicData>) :
    RecyclerView.Adapter<RecommendedMusicAdapter.RecommendedMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMusicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_recommended_music, parent, false)
        return RecommendedMusicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendedMusicViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.stationImage.setImageResource(currentItem.image)
        holder.stationTxt.text = currentItem.txtTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class RecommendedMusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stationImage : ImageView = itemView.findViewById(R.id.station_image)
        val stationTxt : TextView = itemView.findViewById(R.id.station_text)
    }
}
package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.RecommendedMusicData
import hs.project.secondweek.databinding.LayoutRecommendedMusicBinding

class RecommendedMusicAdapter(private val context: Context, private val dataList : ArrayList<RecommendedMusicData>) :
    RecyclerView.Adapter<RecommendedMusicAdapter.RecommendedMusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedMusicViewHolder {
        return RecommendedMusicViewHolder(LayoutRecommendedMusicBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: RecommendedMusicViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.stationImage.setImageResource(currentItem.image)
        holder.stationTxt.text = currentItem.txtTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class RecommendedMusicViewHolder(binding: LayoutRecommendedMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        val stationImage : ImageView = binding.stationImage
        val stationTxt : TextView = binding.stationText
    }
}
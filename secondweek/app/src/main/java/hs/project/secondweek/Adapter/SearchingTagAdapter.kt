package hs.project.secondweek.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.SearchingTagData
import hs.project.secondweek.databinding.LayoutSearchingGridBinding
import kotlin.random.Random

class SearchingTagAdapter(private val context: Context, private val dataList: ArrayList<SearchingTagData>) :
    RecyclerView.Adapter<SearchingTagAdapter.SearchingViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchingTagAdapter.SearchingViewHolder {
        val view = LayoutSearchingGridBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchingTagAdapter.SearchingViewHolder, position: Int) {
        val currentItem = dataList[position]
        var rnd = Random
        var rndColor = Color.argb(160, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        holder.color.setBackgroundColor(rndColor)
        holder.text.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SearchingViewHolder(binding: LayoutSearchingGridBinding) : RecyclerView.ViewHolder(binding.root) {
        val color: ImageView = binding.backColor
        val text: TextView = binding.text
    }

}
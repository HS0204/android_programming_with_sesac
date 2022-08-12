package hs.project.secondweek.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Data.SearchingEditorData
import hs.project.secondweek.databinding.LayoutSearchingLinearBinding

class SearchingEditorAdapter(
    private val context: Context,
    private val dataList: ArrayList<SearchingEditorData>
) : RecyclerView.Adapter<SearchingEditorAdapter.SearchingLinearViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchingEditorAdapter.SearchingLinearViewHolder {
        val view = LayoutSearchingLinearBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchingLinearViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchingEditorAdapter.SearchingLinearViewHolder,
        position: Int
    ) {
        val currentItem = dataList[position]

        holder.img.setImageResource(currentItem.img)
        holder.title.text = currentItem.title
        holder.writer.text = currentItem.writer
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SearchingLinearViewHolder(binding: LayoutSearchingLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img: ImageView = binding.image
        val title: TextView = binding.title
        val writer: TextView = binding.writer
    }
}
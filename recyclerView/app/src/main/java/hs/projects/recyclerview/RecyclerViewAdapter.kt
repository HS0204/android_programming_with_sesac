package hs.projects.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hs.projects.recyclerview.MainActivity.Companion.checkboxList
import hs.projects.recyclerview.databinding.LayoutRecyclerviewBinding

class RecyclerViewAdapter(private val context: Context, private val dataList: ArrayList<Data>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutRecyclerviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(binding: LayoutRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val number: TextView = binding.number
        private val content: TextView = binding.contents
        private val checkBox: CheckBox = binding.checkbox

        fun bind(data: Data, position: Int){
            number.text = data.num.toString()
            content.text = data.content

            if (position >= checkboxList.size) {
                checkboxList.add(position, CheckboxData(data.num, false))
            }

            checkBox.isChecked = checkboxList[position].checked

            checkBox.setOnClickListener {
                checkboxList[position].checked = checkBox.isChecked
            }
        }
    }
}
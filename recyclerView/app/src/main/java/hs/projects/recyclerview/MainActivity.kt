package hs.projects.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.projects.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var dataList: ArrayList<Data>

    companion object {
        var checkboxList = arrayListOf<CheckboxData>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initializeData()
        setAdapter()

    }

    private fun initializeData() {
        dataList = arrayListOf<Data>()

        for (i in 1..21) {
            val data = Data(i, "${i}번째 할 일")
            dataList.add(data)
        }

    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = RecyclerViewAdapter(this, dataList)
    }
}
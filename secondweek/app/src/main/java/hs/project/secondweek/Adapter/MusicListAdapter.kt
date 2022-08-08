package hs.project.secondweek.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.Data.formatDuration
import hs.project.secondweek.ListMusicActivity
import hs.project.secondweek.PlayerMusicActivity
import hs.project.secondweek.R
import hs.project.secondweek.databinding.ActivityMainBinding
import hs.project.secondweek.databinding.LayoutMusicListBinding

class MusicListAdapter(
    private val context: Context,
    private val dataList: ArrayList<MusicInfoData>
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicListAdapter.MusicListViewHolder {
        return MusicListViewHolder(
            LayoutMusicListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicListAdapter.MusicListViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].artUri).apply(
            RequestOptions()
                .placeholder(R.drawable.album_art1).centerCrop()
        )
            .into(holder.img)
        holder.musicTitle.text = dataList[position].title
        holder.musicSinger.text = dataList[position].artist
        holder.musicTime.text = formatDuration(dataList[position].duration)

        holder.root.setOnClickListener {
            val intent = Intent(context, PlayerMusicActivity::class.java)
            intent.putExtra("index", position)
            //intent.putExtra("class", "MusicListAdapter")
            intent.putExtra("musicTitle", dataList[position].title)
            intent.putExtra("musicSinger", dataList[position].artist)
            intent.putExtra("previousActivity", "ListMusicActivity")
            ContextCompat.startActivity(context, intent, null)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MusicListViewHolder(binding: LayoutMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img: ImageView = binding.musicListImg
        val musicTitle: TextView = binding.musicListTitle
        val musicSinger: TextView = binding.musicListSinger
        val musicTime: TextView = binding.musicListTime
        val root = binding.root
    }
}
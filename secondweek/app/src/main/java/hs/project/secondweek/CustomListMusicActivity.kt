package hs.project.secondweek

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.databinding.ActivityCustomListMusicBinding
import hs.project.secondweek.databinding.LayoutPlayingBottomSheetBinding

class CustomListMusicActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCustomListMusicBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "MYLOG"

        var titleCustom: TextView? = null
        var artistCustom: TextView? = null
        var playBtnCustom: ImageView? = null
        var coverCustom: ImageView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "CustomListMusicActivity - onCreate() 호출")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAdapter()
        musicClickListener()

        titleCustom = binding.musicTitle
        artistCustom = binding.musicSinger
        playBtnCustom = binding.musicControl
        coverCustom = binding.musicList

        playBtnCustom?.setOnClickListener {
            Log.d(TAG, "CustomListMusicActivity -> 음악 컨트롤 버튼 클릭")
            if (mediaPlayer!!.isPlaying)
                pauseMusic()
            else if (mediaPlayer != null)
                playMusic()
        }

        binding.musicPlayerSection.setOnClickListener {
            Log.d("MYLOG", "CustomListMusicActivity -> 하단 음악 바 클릭")
            if (customMusicList.size != 0) {
                val intent = Intent(this, PlayerMusicActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "내 음악 -> 로컬 음악에서 리스트를 채워주세요", Toast.LENGTH_SHORT).show()
                    .toString()
            }

        }

    }

    override fun onResume() {
        Log.d(TAG, "CustomListMusicActivity - onResume() 호출")
        super.onResume()
        initializeMiniPlayer()
    }

    private fun setAdapter() {
        Log.d(TAG, "CustomListMusicActivity - RecyclerView 어댑터 세팅")

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.musicListSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        musicListAdapter = MusicListAdapter(this, customMusicList, true)
        recyclerView.adapter = musicListAdapter

    }

    private fun musicClickListener() {


        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this@CustomListMusicActivity, recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val music = MusicInfoData(
                            id = customMusicList[position].id,
                            title = customMusicList[position].title,
                            album = customMusicList[position].album,
                            artist = customMusicList[position].artist,
                            path = customMusicList[position].path,
                            duration = customMusicList[position].duration,
                            artUri = customMusicList[position].artUri
                        )

                        val bottomSheetBinding by lazy { LayoutPlayingBottomSheetBinding.inflate(layoutInflater) }
                        val bottomSheetDialog = BottomSheetDialog(this@CustomListMusicActivity)
                        bottomSheetDialog.setContentView(bottomSheetBinding.root)

                        bottomSheetDialog.show()

                        bottomSheetBinding.playBtn.setOnClickListener {
                            Log.d("MYLOG", "음악 플레이어 $mediaPlayer | 현재 곡 ${music.title}")

                            musicPosition = position

                            mediaPlayer!!.reset()
                            mediaPlayer!!.setDataSource(music.path)
                            mediaPlayer!!.prepare()
                            mediaPlayer!!.start()

                            changeTextTitle = music.title
                            changeTextArtist = music.artist

                            MainActivity.TitleN?.text = changeTextTitle
                            MainActivity.ArtistN?.text = changeTextArtist
                            MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)

                            titleCustom?.text = changeTextTitle
                            artistCustom?.text = changeTextArtist
                            playBtnCustom?.setImageResource(R.drawable.icon_pause)

                            bottomSheetDialog.dismiss()
                        }

                        bottomSheetBinding.deleteBtn.setOnClickListener {
                            Log.d("MYLOG", "${music.title}, customMusicList에서 삭제")

                            musicListAdapter.removeData(position)

                            bottomSheetDialog.dismiss()
                        }
                    }
                })
        )
    }

    private fun initializeMiniPlayer() {
        Log.d(TAG, "ListMusicActivity - 미니 플레이어 초기화")
        titleCustom?.text = changeTextTitle
        artistCustom?.text = changeTextArtist
        titleCustom?.isSingleLine = true
        titleCustom?.isSelected = true
        titleCustom?.ellipsize = TextUtils.TruncateAt.MARQUEE

        if (mediaPlayer == null)
            playBtnCustom?.setImageResource(R.drawable.icon_playing)
        else if (mediaPlayer!!.isPlaying) {
            playBtnCustom?.setImageResource(R.drawable.icon_pause)
        }
        else{
            playBtnCustom?.setImageResource(R.drawable.icon_playing)

            /**
             * 액티비티가 생성되고 나서 딱 한 번만 가능한데, 사용자가 버튼을 누를 때마다 업데이트가 되도록 어떻게 할 수 있을까?
             * 커서를 이용해볼까?
             * **/
            if (!mediaPlayer!!.isPlaying) {
                binding.musicList.setImageResource(R.drawable.icon_music_list)
            }
            else {
                Glide.with(this).load(customMusicList[musicPosition].artUri).apply(
                    RequestOptions()
                        .placeholder(R.drawable.album_art).fitCenter())
                    .into(binding.musicList)
            }
        }

    }

    private fun pauseMusic() {
        playBtnCustom?.setImageResource(R.drawable.icon_playing)
        mediaPlayer!!.pause()
    }

    private fun playMusic() {
        playBtnCustom?.setImageResource(R.drawable.icon_pause)
        mediaPlayer!!.start()
    }
}
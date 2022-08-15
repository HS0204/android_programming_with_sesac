package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import hs.project.secondweek.*
import hs.project.secondweek.Adapter.MusicListAdapter
import hs.project.secondweek.Data.MusicInfoData
import hs.project.secondweek.databinding.FragmentLocalMusicBinding
import hs.project.secondweek.databinding.LayoutBottomSheetBinding

class LocalMusicFragment: Fragment() {

    private lateinit var binding: FragmentLocalMusicBinding

    private lateinit var recyclerView: RecyclerView

    var titleLocal: TextView? = null
    var artistLocal: TextView? = null
    var playBtnLocal: ImageView? = null
    var coverLocal: ImageView? = null

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): LocalMusicFragment {
            return LocalMusicFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MyMusicFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "MyMusicFragment - onCreateView() 호출")
        binding = FragmentLocalMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "MyMusicFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        musicClickListener()

    }

    private fun setAdapter() {
        Log.d(TAG, "MyMusicFragment - RecyclerView 어댑터 세팅")

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.musicListSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        musicListAdapter = MusicListAdapter(requireContext(), localMusicList, false)
        recyclerView.adapter = musicListAdapter

    }

    private fun musicClickListener() {
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(requireContext(), recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val music = MusicInfoData(
                            id = localMusicList[position].id,
                            title = localMusicList[position].title,
                            album = localMusicList[position].album,
                            artist = localMusicList[position].artist,
                            path = localMusicList[position].path,
                            duration = localMusicList[position].duration,
                            artUri = localMusicList[position].artUri
                        )

                        val bottomSheetBinding by lazy { LayoutBottomSheetBinding.inflate(layoutInflater) }
                        val bottomSheetDialog = BottomSheetDialog(requireContext())
                        bottomSheetDialog.setContentView(bottomSheetBinding.root)

                        Glide.with(requireContext()).load(localMusicList[position].artUri).apply(
                            RequestOptions()
                                .placeholder(R.drawable.album_art).fitCenter())
                            .into(bottomSheetBinding.albumArt)

                        bottomSheetBinding.title.text = localMusicList[position].title

                        bottomSheetDialog.show()

                        bottomSheetBinding.addBtn.setOnClickListener {
                            Log.d("MYLOG", "${localMusicList[position].title},  customMusicList에 추가")
                            customMusicList.add(music)
                            selectedMusic = music

                            Log.d("MYLOG", "음악 플레이어 $mediaPlayer 재생 | 현재 곡 ${music.title}")
                            mediaPlayer!!.reset()
                            mediaPlayer!!.setDataSource(music.path)
                            mediaPlayer!!.prepare()
                            mediaPlayer!!.start()

                            changeTextTitle = music.title
                            changeTextArtist = music.artist

                            MainActivity.TitleN?.text = changeTextTitle
                            MainActivity.ArtistN?.text = changeTextArtist
                            MainActivity.PlayN?.setImageResource(R.drawable.icon_pause)

                            CustomListMusicActivity.titleCustom?.text = changeTextTitle
                            CustomListMusicActivity.artistCustom?.text = changeTextArtist
                            CustomListMusicActivity.playBtnCustom?.setImageResource(R.drawable.icon_pause)

                            bottomSheetDialog.dismiss()
                        }
                    }
                })
        )
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "MyMusicFragment - onResume() 호출")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "MyMusicFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "MyMusicFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "MyMusicFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "MyMusicFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "MyMusicFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
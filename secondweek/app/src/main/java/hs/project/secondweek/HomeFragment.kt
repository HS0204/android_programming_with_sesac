package hs.project.secondweek

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var recommendedRecyclerView: RecyclerView
private lateinit var recommendedMusicAdapter : RecommendedMusicAdapter
private lateinit var recommendedMusicDataArray: ArrayList<RecommendedMusicData>
lateinit var recommendedMusicImg : Array<Int>
lateinit var recommendedTxtTitle : Array<String>

private lateinit var newMusicRecyclerView: RecyclerView
private lateinit var newMusicAdapter: NewMusicAdapter
private lateinit var newMusicDataArray: ArrayList<NewMusicData>
lateinit var newImg : Array<Int>
lateinit var newTxtTitle : Array<String>
lateinit var newTxtSinger : Array<String>

private lateinit var videoMusicRecyclerView: RecyclerView
private lateinit var videoMusicAdapter: VideoMusicAdapter
private lateinit var videoMusicDataArray: ArrayList<VideoMusicData>
lateinit var videoImg : Array<Int>
lateinit var videoTxtTitle : Array<String>
lateinit var videoTxtSinger : Array<String>

class HomeFragment : Fragment() {
    companion object {
        const val TAG : String = "MYLOG"

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment - onCreate() 호출")
    }

    // 프레그먼트가 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment - onAttach() 호출")
    }

    // 뷰가 생성되었을 때 -> 레이아웃과 뷰를 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreatView() 호출")
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "HomeFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()

        // 추천 곡
        val recommendLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recommendedRecyclerView = view.findViewById(R.id.recommended_music_section)
        recommendedRecyclerView.layoutManager = recommendLayoutManager
        recommendedRecyclerView.setHasFixedSize(true)

        recommendedMusicAdapter = RecommendedMusicAdapter(recommendedMusicDataArray)
        recommendedRecyclerView.adapter = recommendedMusicAdapter

        // 최신 곡
        val newLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        newMusicRecyclerView = view.findViewById(R.id.new_music_section)
        newMusicRecyclerView.layoutManager = newLayoutManager
        newMusicRecyclerView.setHasFixedSize(true)

        newMusicAdapter = NewMusicAdapter(newMusicDataArray)
        newMusicRecyclerView.adapter = newMusicAdapter

        // 영상
        val videoLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        videoMusicRecyclerView = view.findViewById(R.id.video_music_section)
        videoMusicRecyclerView.layoutManager = videoLayoutManager
        videoMusicRecyclerView.setHasFixedSize(true)

        videoMusicAdapter = VideoMusicAdapter(videoMusicDataArray)
        videoMusicRecyclerView.adapter = videoMusicAdapter

    }

    private fun dataInitialize() {
        // 추천 곡
        recommendedMusicDataArray = arrayListOf<RecommendedMusicData>()

        recommendedMusicImg = arrayOf(
            R.drawable.station_1, R.drawable.station_2, R.drawable.station_3, R.drawable.station_4,
            R.drawable.station_5
        )

        recommendedTxtTitle = arrayOf(
            "Kygo, Steve Aoki, HRVY", "Lucia의 팬이라면", "수현, 플레이문, Lubless", "Anne-Marie, 마마무, 베이식",
            "The 5th Mini Album 'Can ...'"
        )

        for (i in recommendedMusicImg.indices) {
            val recommendedMusicData = RecommendedMusicData(recommendedMusicImg[i], recommendedTxtTitle[i])
            recommendedMusicDataArray.add(recommendedMusicData)
        }

        // 최신 곡
        newMusicDataArray = arrayListOf<NewMusicData>()

        newImg = arrayOf(
            R.drawable.new_1, R.drawable.new_2, R.drawable.new_3, R.drawable.new_4, R.drawable.new_5,
            R.drawable.new_6, R.drawable.new_7, R.drawable.new_8, R.drawable.new_9, R.drawable.new_10,
            R.drawable.new_11, R.drawable.new_12, R.drawable.new_13, R.drawable.new_14, R.drawable.new_15,
            R.drawable.new_16, R.drawable.new_17, R.drawable.new_18
        )

        newTxtTitle = arrayOf(
            "요즘 신곡", "Fried Egg", "FOREVER 1-...", "Bad Decisions", "Still Sunset",
            "아는동생", "오늘부터...", "화염방", "Live Love Dre...", "묘 (Cat)",
            "사랑은 늘 그렇...", "All 4 Nothing", "Funk Wav Bo...", "Blue", "Dance all night",
            "First Wave", "청춘스타 Part12", "자기야 사랑해"
        )

        newTxtSinger = arrayOf(
            "화/금 업데이트", "EB", "소녀시대", "benny blanco, 방...", "넬",
            "은비디아", "홍이삭", "탁", "션케이", "Colde",
            "라운드어바웃", "Lauv", "Calvin Harris", "현성", "방민혁",
            "Ordinary Surfers", "종한, 김푸름 외", "COVA"
        )

        for (i in newImg.indices) {
            val newMusicData = NewMusicData(newImg[i], newTxtTitle[i], newTxtSinger[i])
            newMusicDataArray.add(newMusicData)
        }

        // 영상
        videoMusicDataArray = arrayListOf<VideoMusicData>()

        videoImg = arrayOf(
            R.drawable.video_1, R.drawable.video_2, R.drawable.video_3, R.drawable.video_4
        )

        videoTxtTitle = arrayOf(
            "아는동생", "SMARTPHONE", "Attention", "FOREVER 1"
        )

        videoTxtSinger = arrayOf(
            "은비디아", "YENA (최예나)", "NewJeans", "소녀시대 (GIRLS' GENERATION)"
        )

        for (i in videoImg.indices) {
            val videoMusicData = VideoMusicData(videoImg[i], videoTxtTitle[i], videoTxtSinger[i])
            videoMusicDataArray.add(videoMusicData)
        }
    }

}
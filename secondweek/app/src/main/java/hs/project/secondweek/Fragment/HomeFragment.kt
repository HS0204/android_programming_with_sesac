package hs.project.secondweek.Fragment

import android.annotation.SuppressLint
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
import hs.project.secondweek.*
import hs.project.secondweek.Adapter.NewMusicAdapter
import hs.project.secondweek.Adapter.RecommendedMusicAdapter
import hs.project.secondweek.Adapter.VideoMusicAdapter
import hs.project.secondweek.Data.NewMusicData
import hs.project.secondweek.Data.RecommendedMusicData
import hs.project.secondweek.Data.VideoMusicData
import hs.project.secondweek.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var owner:Owner

    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedMusicAdapter: RecommendedMusicAdapter
    private lateinit var recommendedMusicDataArray: ArrayList<RecommendedMusicData>
    lateinit var recommendedMusicImg: Array<Int>
    lateinit var recommendedTxtTitle: Array<String>

    private lateinit var newMusicRecyclerView: RecyclerView
    private lateinit var newMusicAdapter: NewMusicAdapter
    private lateinit var newMusicDataArray: ArrayList<NewMusicData>
    lateinit var newImg: Array<Int>
    lateinit var newTxtTitle: Array<String>
    lateinit var newTxtSinger: Array<String>

    private lateinit var videoMusicRecyclerView: RecyclerView
    private lateinit var videoMusicAdapter: VideoMusicAdapter
    private lateinit var videoMusicDataArray: ArrayList<VideoMusicData>
    lateinit var videoImg: Array<Int>
    lateinit var videoTxtTitle: Array<String>
    lateinit var videoTxtSinger: Array<String>

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "HomeFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        owner = context as Owner
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "HomeFragment - onCreateView() 호출")

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "HomeFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        if (isLogin) {
            binding.recommendedMusicHeader.text = "${userName}님을 위한 추천"
            binding.recommendedMusicHeader.visibility = View.VISIBLE
            binding.recommendedMusicSection.visibility = View.VISIBLE
        }
        else {
            binding.recommendedMusicHeader.visibility = View.GONE
            binding.recommendedMusicSection.visibility = View.GONE
        }

        initializeData()
        setAdapter()

        binding.optionIcon.setOnClickListener {
            MainActivity.miniPlayer!!.visibility = View.INVISIBLE
            MainActivity.bottomNav!!.visibility = View.INVISIBLE

            owner.replaceFragment(SettingFragment.newInstance())

        }

        MainActivity.miniPlayer!!.visibility = View.VISIBLE
        MainActivity.bottomNav!!.visibility = View.VISIBLE

        binding.videoMusicSection.setPadding(0, 0, 0, recyclerViewBottomPadding)
    }

    private fun initializeData() {
        Log.d(MainActivity.TAG, "MainActivity - 데이터 초기화")

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
            val recommendedMusicData =
                RecommendedMusicData(recommendedMusicImg[i], recommendedTxtTitle[i])
            recommendedMusicDataArray.add(recommendedMusicData)
        }

        // 최신 곡
        newMusicDataArray = arrayListOf<NewMusicData>()

        newImg = arrayOf(
            R.drawable.new_1,
            R.drawable.new_2,
            R.drawable.new_3,
            R.drawable.new_4,
            R.drawable.new_5,
            R.drawable.new_6,
            R.drawable.new_7,
            R.drawable.new_8,
            R.drawable.new_9,
            R.drawable.new_10,
            R.drawable.new_11,
            R.drawable.new_12,
            R.drawable.new_13,
            R.drawable.new_14,
            R.drawable.new_15,
            R.drawable.new_16,
            R.drawable.new_17,
            R.drawable.new_18
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

    private fun setAdapter() {
        Log.d(MainActivity.TAG, "MainActivity - 어댑터 생성")

        // 추천 곡
        val recommendLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recommendedRecyclerView = binding.recommendedMusicSection
        recommendedRecyclerView.layoutManager = recommendLayoutManager
        recommendedRecyclerView.setHasFixedSize(true)

        recommendedMusicAdapter = RecommendedMusicAdapter(requireActivity(), recommendedMusicDataArray)
        recommendedRecyclerView.adapter = recommendedMusicAdapter

        // 최신 곡
        val newLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        newMusicRecyclerView = binding.newMusicSection
        newMusicRecyclerView.layoutManager = newLayoutManager
        newMusicRecyclerView.setHasFixedSize(true)

        newMusicAdapter = NewMusicAdapter(requireActivity(), newMusicDataArray)
        newMusicRecyclerView.adapter = newMusicAdapter

        // 영상
        val videoLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        videoMusicRecyclerView = binding.videoMusicSection
        videoMusicRecyclerView.layoutManager = videoLayoutManager
        videoMusicRecyclerView.setHasFixedSize(true)

        videoMusicAdapter = VideoMusicAdapter(requireActivity(), videoMusicDataArray)
        videoMusicRecyclerView.adapter = videoMusicAdapter
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "HomeFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "HomeFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "HomeFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "HomeFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "HomeFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "HomeFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
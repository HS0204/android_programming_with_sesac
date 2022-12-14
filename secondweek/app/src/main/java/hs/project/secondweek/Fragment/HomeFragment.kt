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
        Log.d(TAG, "HomeFragment - onCreate() ??????")
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
        Log.d(TAG, "HomeFragment - onCreateView() ??????")

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ??????????????? ??? ????????? ?????? -> RecyclerView ????????? ???
        Log.d(TAG, "HomeFragment - onViewCreated() ??????")
        super.onViewCreated(view, savedInstanceState)

        if (isLogin) {
            binding.recommendedMusicHeader.text = "${userName}?????? ?????? ??????"
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
        Log.d(MainActivity.TAG, "MainActivity - ????????? ?????????")

        // ?????? ???
        recommendedMusicDataArray = arrayListOf<RecommendedMusicData>()

        recommendedMusicImg = arrayOf(
            R.drawable.station_1, R.drawable.station_2, R.drawable.station_3, R.drawable.station_4,
            R.drawable.station_5
        )

        recommendedTxtTitle = arrayOf(
            "Kygo, Steve Aoki, HRVY", "Lucia??? ????????????", "??????, ????????????, Lubless", "Anne-Marie, ?????????, ?????????",
            "The 5th Mini Album 'Can ...'"
        )

        for (i in recommendedMusicImg.indices) {
            val recommendedMusicData =
                RecommendedMusicData(recommendedMusicImg[i], recommendedTxtTitle[i])
            recommendedMusicDataArray.add(recommendedMusicData)
        }

        // ?????? ???
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
            "?????? ??????", "Fried Egg", "FOREVER 1-...", "Bad Decisions", "Still Sunset",
            "????????????", "????????????...", "?????????", "Live Love Dre...", "??? (Cat)",
            "????????? ??? ??????...", "All 4 Nothing", "Funk Wav Bo...", "Blue", "Dance all night",
            "First Wave", "???????????? Part12", "????????? ?????????"
        )

        newTxtSinger = arrayOf(
            "???/??? ????????????", "EB", "????????????", "benny blanco, ???...", "???",
            "????????????", "?????????", "???", "?????????", "Colde",
            "??????????????????", "Lauv", "Calvin Harris", "??????", "?????????",
            "Ordinary Surfers", "??????, ????????? ???", "COVA"
        )

        for (i in newImg.indices) {
            val newMusicData = NewMusicData(newImg[i], newTxtTitle[i], newTxtSinger[i])
            newMusicDataArray.add(newMusicData)
        }

        // ??????
        videoMusicDataArray = arrayListOf<VideoMusicData>()

        videoImg = arrayOf(
            R.drawable.video_1, R.drawable.video_2, R.drawable.video_3, R.drawable.video_4
        )

        videoTxtTitle = arrayOf(
            "????????????", "SMARTPHONE", "Attention", "FOREVER 1"
        )

        videoTxtSinger = arrayOf(
            "????????????", "YENA (?????????)", "NewJeans", "???????????? (GIRLS' GENERATION)"
        )

        for (i in videoImg.indices) {
            val videoMusicData = VideoMusicData(videoImg[i], videoTxtTitle[i], videoTxtSinger[i])
            videoMusicDataArray.add(videoMusicData)
        }
    }

    private fun setAdapter() {
        Log.d(MainActivity.TAG, "MainActivity - ????????? ??????")

        // ?????? ???
        val recommendLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recommendedRecyclerView = binding.recommendedMusicSection
        recommendedRecyclerView.layoutManager = recommendLayoutManager
        recommendedRecyclerView.setHasFixedSize(true)

        recommendedMusicAdapter = RecommendedMusicAdapter(requireActivity(), recommendedMusicDataArray)
        recommendedRecyclerView.adapter = recommendedMusicAdapter

        // ?????? ???
        val newLayoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        newMusicRecyclerView = binding.newMusicSection
        newMusicRecyclerView.layoutManager = newLayoutManager
        newMusicRecyclerView.setHasFixedSize(true)

        newMusicAdapter = NewMusicAdapter(requireActivity(), newMusicDataArray)
        newMusicRecyclerView.adapter = newMusicAdapter

        // ??????
        val videoLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        videoMusicRecyclerView = binding.videoMusicSection
        videoMusicRecyclerView.layoutManager = videoLayoutManager
        videoMusicRecyclerView.setHasFixedSize(true)

        videoMusicAdapter = VideoMusicAdapter(requireActivity(), videoMusicDataArray)
        videoMusicRecyclerView.adapter = videoMusicAdapter
    }

    override fun onResume() {
        // ?????????????????? ????????? ???????????? ?????? -> ??????, ??????, ????????? ???
        Log.d(TAG, "HomeFragment - onResume() ??????")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "HomeFragment - onPause() ??????")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "HomeFragment - onStop() ??????")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 ???????????? onStop() ?????? ??????. 28 ????????? ?????? ??????
        Log.d(TAG, "HomeFragment - onSaveInstanceState() ??????")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "HomeFragment - onDestroyView() ??????")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "HomeFragment - onDestroy() ??????")
        super.onDestroy()
    }

}
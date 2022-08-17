package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.Music4uAdapter
import hs.project.secondweek.Data.Music4uData
import hs.project.secondweek.R
import hs.project.secondweek.databinding.FragmentMusic4uBinding
import hs.project.secondweek.recyclerViewBottomPadding

class Music4UFragment: Fragment() {

    private lateinit var binding: FragmentMusic4uBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Music4uAdapter
    private lateinit var dataArray: ArrayList<Music4uData>

    private lateinit var albumImg: Array<Int>
    lateinit var title: Array<String>
    lateinit var singers: Array<String>

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): Music4UFragment {
            return Music4UFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Music4UFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Music4UFragment - onCreateView() 호출")
        binding = FragmentMusic4uBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Music4UFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        initializeData()
        setAdapter()

        binding.music4uSection.setPadding(0, 0, 0, recyclerViewBottomPadding)
    }

    private fun initializeData() {
        Log.d(TAG, "Music4UFragment - 데이터 초기화")

        dataArray = arrayListOf<Music4uData>()

        albumImg = arrayOf(
            R.drawable.music4u_1, R.drawable.music4u_2, R.drawable.music4u_3, R.drawable.music4u_4,
            R.drawable.music4u_5, R.drawable.music4u_6, R.drawable.music4u_7, R.drawable.music4u_8
        )

        title = arrayOf(
            "즐겨들은 'L.O.V.E (feat. EAR...' 분위기의 곡", "내 취향 최신 곡", "국내 팝 취향의 추천 음악", "작년 오늘 들었던 노래",
            "꼰대가 선정한 텐션업 여름리스트", "국내 팝에서 요즘 핫한 곡", "아이돌이 부르는 딥하우스", "Red Velvet(레드벨벳)의 인기곡"
        )

        singers = arrayOf(
            "Young Bae, John K, Singala", "Weeekly, Ordinary Surfers, Just Kevin", "EXO, 윤하, Billlie", "SHINee, 페이퍼컷 프로젝트, Lucia",
            "김건모, 언타이틀, 이정", "로꼬, 화사, 이무진", "f, SHINee, 루나", "Red Velvet"
        )

        for (i in albumImg.indices) {
            val data =
                Music4uData(albumImg[i], title[i], singers[i])
            dataArray.add(data)
        }
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView = binding.music4uSection
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        adapter = Music4uAdapter(requireContext(), dataArray)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "Music4UFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "Music4UFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "Music4UFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "Music4UFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "Music4UFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "Music4UFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.SearchingEditorAdapter
import hs.project.secondweek.Adapter.SearchingTagAdapter
import hs.project.secondweek.Data.SearchingEditorData
import hs.project.secondweek.Data.SearchingTagData
import hs.project.secondweek.R
import hs.project.secondweek.databinding.FragmentSearchingBinding

class SearchingFragment : Fragment() {

    private lateinit var binding: FragmentSearchingBinding

    private lateinit var recyclerViewTag: RecyclerView
    private lateinit var adapterTag: SearchingTagAdapter
    private lateinit var dataArrayTag: ArrayList<SearchingTagData>

    private lateinit var recyclerViewEditor: RecyclerView
    private lateinit var adapterEditor: SearchingEditorAdapter
    private lateinit var dataArrayEditor: ArrayList<SearchingEditorData>
    lateinit var imgEditor: Array<Int>
    lateinit var titleEditor: Array<String>
    lateinit var writerEditor: Array<String>

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): SearchingFragment {
            return SearchingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchingFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "SearchingFragment - onCreateView() 호출")
        binding = FragmentSearchingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchingFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        initializeData()
        setAdapter()
    }

    private fun initializeData() {
        Log.d(Music4UFragment.TAG, "SearchingFragment - 데이터 초기화")

        // 태그 추천
        dataArrayTag = arrayListOf<SearchingTagData>()

        for (i in 1..30) {
            val data = SearchingTagData("# $i")
            dataArrayTag.add(data)
        }

        // 에디터 추천
        dataArrayEditor = arrayListOf<SearchingEditorData>()

        imgEditor = arrayOf(
            R.drawable.editor_1,
            R.drawable.editor_2,
            R.drawable.editor_3,
            R.drawable.editor_4
        )

        titleEditor =
            arrayOf(
                "팝송으로 배우는 영어! 60.Conan Gray - 'Disaster'",
                "벅스 나눔 캠페인 - 벅스 X 카라 (동물권행동)",
                "트렌디 라틴뮤직 Hits: 2022년 8월",
                "2022 한국대중음악상 수상작 모음"
            )

        writerEditor = arrayOf("뮤직&", "주간 핫이슈", "라틴Hits", "벅스PD")

        for (i in imgEditor.indices) {
            val data = SearchingEditorData(imgEditor[i], titleEditor[i], writerEditor[i])
            dataArrayEditor.add(data)
        }

    }

    private fun setAdapter() {
        // 태그 추천
        val layoutManagerTag = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        recyclerViewTag = binding.tagMusicRecommended
        recyclerViewTag.layoutManager = layoutManagerTag
        recyclerViewTag.setHasFixedSize(true)

        adapterTag = SearchingTagAdapter(requireContext(), dataArrayTag)
        recyclerViewTag.adapter = adapterTag

        // 에디터 추천
        val layoutManagerEditor = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerViewEditor = binding.editorMusicRecommended
        recyclerViewEditor.layoutManager = layoutManagerEditor
        recyclerViewEditor.setHasFixedSize(true)

        adapterEditor = SearchingEditorAdapter(requireContext(), dataArrayEditor)
        recyclerViewEditor.adapter = adapterEditor
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "SearchingFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "SearchingFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "SearchingFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "SearchingFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "SearchingFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "SearchingFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
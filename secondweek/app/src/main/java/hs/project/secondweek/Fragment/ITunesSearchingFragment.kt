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
import hs.project.secondweek.databinding.FragmentItunesSearchingBinding
import hs.project.secondweek.databinding.FragmentSearchingBinding
import hs.project.secondweek.recyclerViewBottomPadding

class ITunesSearchingFragment : Fragment() {

    private lateinit var binding: FragmentItunesSearchingBinding

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): ITunesSearchingFragment {
            return ITunesSearchingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "ITunesSearchingFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "ITunesSearchingFragment - onCreateView() 호출")
        binding = FragmentItunesSearchingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "ITunesSearchingFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        binding.searchedArtist.setPadding(0, 0, 0, recyclerViewBottomPadding)
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "ITunesSearchingFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "ITunesSearchingFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "ITunesSearchingFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "ITunesSearchingFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "ITunesSearchingFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "ITunesSearchingFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
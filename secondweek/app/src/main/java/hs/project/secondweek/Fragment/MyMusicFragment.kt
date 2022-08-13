package hs.project.secondweek.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hs.project.secondweek.ListMusicActivity
import hs.project.secondweek.databinding.FragmentMymusicBinding
import hs.project.secondweek.localMusicList

class MyMusicFragment: Fragment() {

    private lateinit var binding: FragmentMymusicBinding

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): MyMusicFragment {
            return MyMusicFragment()
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
        binding = FragmentMymusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "MyMusicFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        binding.localCount.text = "${localMusicList.size}곡"

        binding.localSection.setOnClickListener {
            val intent = Intent(activity, ListMusicActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            // !!!!!!!!음악 액티비티 팝업 애니메이션 필요
        }

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
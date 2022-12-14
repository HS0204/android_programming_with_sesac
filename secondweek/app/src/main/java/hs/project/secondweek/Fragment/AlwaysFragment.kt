package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hs.project.secondweek.R

class AlwaysFragment: Fragment() {

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): AlwaysFragment {
            return AlwaysFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "AlwaysFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 inflate
        Log.d(TAG, "AlwaysFragment - onCreateView() 호출")
        return inflater.inflate(R.layout.fragment_always, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "AlwaysFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "AlwaysFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "AlwaysFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "AlwaysFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "AlwaysFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "AlwaysFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "AlwaysFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
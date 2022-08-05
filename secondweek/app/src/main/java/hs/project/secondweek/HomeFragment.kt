package hs.project.secondweek

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

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

}
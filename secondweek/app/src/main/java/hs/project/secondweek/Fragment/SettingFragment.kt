package hs.project.secondweek.Fragment

import android.content.Context
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
import hs.project.secondweek.databinding.FragmentLoginBinding
import hs.project.secondweek.databinding.FragmentSettingBinding
import hs.project.secondweek.databinding.LayoutBottomSheetBinding

class SettingFragment: Fragment() {

    private lateinit var binding: FragmentSettingBinding
    lateinit var owner:Owner

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "SettingFragment - onCreate() 호출")
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
        Log.d(TAG, "SettingFragment - onCreateView() 호출")
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "SettingFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            owner.replaceFragment(LoginFragment.newInstance())
        }
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "SettingFragment - onResume() 호출")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "SettingFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "SettingFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "SettingFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "SettingFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "SettingFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
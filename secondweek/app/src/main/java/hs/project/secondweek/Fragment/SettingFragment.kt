package hs.project.secondweek.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hs.project.secondweek.*
import hs.project.secondweek.databinding.*

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
        Log.d(TAG, "SettingLogoutFragment - onCreate() 호출")
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
        Log.d(TAG, "SettingLogoutFragment - onCreateView() 호출")
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 프래그먼트 뷰 초기값 설정 -> RecyclerView 어댑터 등
        Log.d(TAG, "SettingLogoutFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        if (isLogin) {
            binding.userName.text = userName
            binding.loginState.visibility = View.VISIBLE
            binding.logoutState.visibility = View.GONE

            binding.userInfoBtn.setOnClickListener {
                owner.replaceFragment(UserInfoFragment.newInstance())
            }

        } else {
            binding.loginState.visibility = View.GONE
            binding.logoutState.visibility = View.VISIBLE
        }

        binding.loginBtn.setOnClickListener {
            owner.replaceFragment(LoginFragment.newInstance())
        }
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "SettingLogoutFragment - onResume() 호출")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "SettingLogoutFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "SettingLogoutFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "SettingLogoutFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "SettingLogoutFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "SettingLogoutFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
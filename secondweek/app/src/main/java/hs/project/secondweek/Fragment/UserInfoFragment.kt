package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import hs.project.secondweek.databinding.FragmentUserInfoBinding
import hs.project.secondweek.isLogin
import hs.project.secondweek.userName


class UserInfoFragment: Fragment() {

    private lateinit var binding: FragmentUserInfoBinding

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): UserInfoFragment {
            return UserInfoFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "UserInfoFragment - onCreate() 호출")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "UserInfoFragment - onCreateView() 호출")
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "UserInfoFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        binding.nicknameUser.text = userName

        binding.userInfoBtn.setOnClickListener {
            kakaoTokenCheck()
        }

    }

    fun kakaoTokenCheck() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                        //로그인 필요
                        Log.d(TAG, "로그인 해야 함")
                    }
                    else {
                        //기타 에러
                        Log.d(TAG, "기타 에러")
                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    Log.d(TAG, "토큰 유효성 체크 성공")
                    //kakaoLogout()
                    kakaoUnlink()
                }
            }
        }
        else {
            //로그인 필요
            Log.d(TAG, "로그인 해야 함")
        }
    }

    fun kakaoLogout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.d(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.d(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                isLogin = false
            }
        }
    }

    fun kakaoUnlink() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.d(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.d(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                Toast.makeText(requireContext(), "로그아웃에 성공했습니다", Toast.LENGTH_SHORT).show()
                isLogin = false
            }
        }
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "UserInfoFragment - onResume() 호출")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "UserInfoFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "UserInfoFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "UserInfoFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "UserInfoFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "LoginFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
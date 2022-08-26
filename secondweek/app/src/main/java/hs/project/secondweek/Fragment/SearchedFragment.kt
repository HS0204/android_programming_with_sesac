package hs.project.secondweek.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import hs.project.secondweek.Owner
import hs.project.secondweek.databinding.FragmentSearchedBinding

lateinit var keyword: String

class SearchedFragment : Fragment() {

    private lateinit var binding: FragmentSearchedBinding
    lateinit var owner:Owner

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): SearchedFragment {
            return SearchedFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchedFragment - onCreate() 호출")
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
        Log.d(TAG, "SearchedFragment - onCreateView() 호출")
        binding = FragmentSearchedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "SearchedFragment - onViewCreated() 호출")
        super.onViewCreated(view, savedInstanceState)

        binding.searchingBar.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                keyBoardDown()

                keyword = binding.searchingBar.text.toString()
                owner.replaceFragment(ITunesSearchingFragment.newInstance())
            }
            true
        })
    }

    private fun keyBoardDown() {
        val keyBoardDown = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoardDown.hideSoftInputFromWindow(binding.searchingBar.windowToken, 0)
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "SearchedFragment - onResume() 호출")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "SearchedFragment - onPause() 호출")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "SearchedFragment - onStop() 호출")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // API 28 이상부터 onStop() 이후 호출. 28 이하는 이전 호출
        Log.d(TAG, "SearchedFragment - onSaveInstanceState() 호출")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "SearchedFragment - onDestroyView() 호출")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "SearchedFragment - onDestroy() 호출")
        super.onDestroy()
    }

}
package hs.project.secondweek.Fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hs.project.secondweek.Adapter.MusicITunesAdapter
import hs.project.secondweek.Data.MusicITunesData
import hs.project.secondweek.Data.Result
import hs.project.secondweek.Owner
import hs.project.secondweek.SearchingService
import hs.project.secondweek.databinding.FragmentItunesSearchingBinding
import hs.project.secondweek.recyclerViewBottomPadding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ITunesSearchingFragment : Fragment() {

    private lateinit var binding: FragmentItunesSearchingBinding
    lateinit var owner:Owner

    private lateinit var musicITunesAdapter: MusicITunesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG: String = "MYLOG"

        fun newInstance(): ITunesSearchingFragment {
            return ITunesSearchingFragment()
        }
    }

    object RetrofitClient {
        private const val baseUrl = "https://itunes.apple.com/"
        private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SearchingService = retrofit.create(SearchingService::class.java)
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

        initializeData()

        binding.searchingBar.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_ENTER) {
                keyword = binding.searchingBar.text.toString()
                initializeData()
            }
            true
        })

        binding.searchedArtist.setPadding(0, 0, 0, recyclerViewBottomPadding)
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "ITunesSearchingFragment - onResume() 호출")
        super.onResume()
    }

    private fun setAdapter(data: List<Result>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView = binding.searchedMusic
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        musicITunesAdapter = MusicITunesAdapter(requireContext(), data)
        recyclerView.adapter = musicITunesAdapter

    }

    private fun initializeData() {
        val searchingService = RetrofitClient.service.getMusic(keyword, 5, "song")

        searchingService.enqueue(object: Callback<MusicITunesData> {
            override fun onResponse(
                call: Call<MusicITunesData>,
                response: Response<MusicITunesData>
            ) {
                val body = response.body()

                if (body != null) {
                    Log.d("TEST", "현재 키워드 : $keyword")
                    Log.d("TEST", "통신 성공")
                    setAdapter(body.results)
                }
                else {
                    Log.d("TEST","바디 null")
                }
            }

            override fun onFailure(call: Call<MusicITunesData>, t: Throwable) {
                Log.d("TEST", "통신 실패", t)
            }
        })
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
package hs.project.secondweek.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hs.project.secondweek.Adapter.MusicITunesAdapter
import hs.project.secondweek.Adapter.MusicSearchArtistAdapter
import hs.project.secondweek.Data.*
import hs.project.secondweek.Owner
import hs.project.secondweek.SearchingService
import hs.project.secondweek.databinding.FragmentItunesSearchingBinding
import hs.project.secondweek.recyclerViewBottomPadding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ITunesSearchingFragment : Fragment() {

    private lateinit var binding: FragmentItunesSearchingBinding
    lateinit var owner:Owner

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

        binding.searchingBar.setText(keyword)

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
                keyBoardDown()
            }
            true
        })

        binding.searchedArtist.setPadding(0, 0, 0, recyclerViewBottomPadding)
    }

    private fun keyBoardDown() {
        val keyBoardDown = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoardDown.hideSoftInputFromWindow(binding.searchingBar.windowToken, 0)
    }

    private fun initializeData() {
        searchingMusic()
        similarArtist()
    }

    override fun onResume() {
        // 프래그먼트와 사용자 상호작용 가능 -> 동작, 입력, 포커스 등
        Log.d(TAG, "ITunesSearchingFragment - onResume() 호출")
        super.onResume()
    }

    private fun setMusicAdapter(data: List<ITunesResult>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val recyclerView = binding.searchedMusic
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val adapter = MusicITunesAdapter(requireContext(), data)
        recyclerView.adapter = adapter

    }

    private fun setArtistAdapter(data: List<SimilarArtist>) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val recyclerView = binding.searchedArtist
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val adapter = MusicSearchArtistAdapter(requireContext(), data)
        recyclerView.adapter = adapter

    }

    private fun searchingMusic() {
        val baseUrl = "https://itunes.apple.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SearchingService = retrofit.create(SearchingService::class.java)
        val searchingMusic = service.getMusic(keyword, 5, "song")

        searchingMusic.enqueue(object: Callback<MusicITunesData> {
            override fun onResponse(
                call: Call<MusicITunesData>,
                response: Response<MusicITunesData>
            ) {
                Log.d("Retrofit", "곡 찾기 | 현재 키워드 : $keyword")
                val body = response.body()

                if (body != null) {
                    Log.d("Retrofit", "곡 찾기 | 통신 성공")
                    setMusicAdapter(body.results)
                }
                else {
                    Log.d("Retrofit","곡 찾기 | 바디 null")
                }
            }

            override fun onFailure(call: Call<MusicITunesData>, t: Throwable) {
                Log.d("Retrofit", "곡 찾기 | 통신 실패", t)
            }
        })
    }

    private fun similarArtist() {
        val baseUrl = " https://tastedive.com/api/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SearchingService = retrofit.create(SearchingService::class.java)
        val searchingArtist = service.searchSimilarArtist(keyword, 1, "music", 3)

        searchingArtist.enqueue(object : Callback<MusicSimilarData> {
            override fun onResponse(call: Call<MusicSimilarData>, response: Response<MusicSimilarData>) {
                Log.d("Retrofit", "가수 찾기 | 현재 키워드 : $keyword")
                val body = response.body()

                if (body != null) {
                    Log.d("Retrofit", "가수 찾기 | 통신 성공")
                    var artistList = ArrayList<SimilarArtist>()

                    for (i in 0 until body.Similar.Info.size) {
                        artistList.add(body.Similar.Info[i])
                    }

                    for (i in 0 until body.Similar.Results.size) {
                        artistList.add(body.Similar.Results[i])
                    }

                    setArtistAdapter(artistList)
                }
                else {
                    Log.d("Retrofit","가수 찾기 | 바디 null")
                }
            }

            override fun onFailure(call: Call<MusicSimilarData>, t: Throwable) {
                Log.d("Retrofit", "가수 찾기 | 통신 실패", t)
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
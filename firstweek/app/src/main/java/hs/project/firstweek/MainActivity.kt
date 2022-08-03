package hs.project.firstweek

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import hs.project.firstweek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.domesticStocksBtn.setOnClickListener {
            binding.domesticStocksView.visibility = View.VISIBLE
            binding.domesticStocksBtn.setTextColor(Color.parseColor("#1F1F1F"))
            binding.domesticStocksBtn.setTypeface(null, Typeface.BOLD)

            binding.overseasStocksView.visibility = View.INVISIBLE
            binding.overseasStocksBtn.setTextColor(Color.parseColor("#434343"))
            binding.overseasStocksBtn.setTypeface(null, Typeface.NORMAL)

            binding.pensionView.visibility = View.INVISIBLE
            binding.pensionBtn.setTextColor(Color.parseColor("#434343"))
            binding.pensionBtn.setTypeface(null, Typeface.NORMAL)

        }
        binding.overseasStocksBtn.setOnClickListener {
            binding.domesticStocksView.visibility = View.INVISIBLE
            binding.domesticStocksBtn.setTextColor(Color.parseColor("#434343"))
            binding.domesticStocksBtn.setTypeface(null, Typeface.NORMAL)

            binding.overseasStocksView.visibility = View.VISIBLE
            binding.overseasStocksBtn.setTextColor(Color.parseColor("#1F1F1F"))
            binding.overseasStocksBtn.setTypeface(null, Typeface.BOLD)

            binding.pensionView.visibility = View.INVISIBLE
            binding.pensionBtn.setTextColor(Color.parseColor("#434343"))
            binding.pensionBtn.setTypeface(null, Typeface.NORMAL)
        }
        binding.pensionBtn.setOnClickListener {
            binding.domesticStocksView.visibility = View.INVISIBLE
            binding.domesticStocksBtn.setTextColor(Color.parseColor("#434343"))
            binding.domesticStocksBtn.setTypeface(null, Typeface.NORMAL)

            binding.overseasStocksView.visibility = View.INVISIBLE
            binding.overseasStocksBtn.setTextColor(Color.parseColor("#434343"))
            binding.overseasStocksBtn.setTypeface(null, Typeface.NORMAL)

            binding.pensionView.visibility = View.VISIBLE
            binding.pensionBtn.setTextColor(Color.parseColor("#1F1F1F"))
            binding.pensionBtn.setTypeface(null, Typeface.BOLD)
        }

    }



}
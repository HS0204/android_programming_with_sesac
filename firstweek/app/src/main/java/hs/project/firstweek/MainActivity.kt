package hs.project.firstweek

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    lateinit var domesticBtn: Button
    lateinit var overseasBtn: Button
    lateinit var pensionBtn: Button

    lateinit var domesticView: ImageView
    lateinit var overseasView: ImageView
    lateinit var pensionView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        domesticBtn = findViewById(R.id.domestic_stocks_btn)
        overseasBtn = findViewById(R.id.overseas_stocks_btn)
        pensionBtn = findViewById(R.id.pension_btn)

        domesticView = findViewById(R.id.domestic_stocks_view)
        overseasView = findViewById(R.id.overseas_stocks_view)
        pensionView = findViewById(R.id.pension_view)

        domesticBtn.setOnClickListener {
            domesticView.visibility = View.VISIBLE
            domesticBtn.setTextColor(Color.parseColor("#1F1F1F"))
            domesticBtn.setTypeface(null, Typeface.BOLD)

            overseasView.visibility = View.INVISIBLE
            overseasBtn.setTextColor(Color.parseColor("#434343"))
            overseasBtn.setTypeface(null, Typeface.NORMAL)

            pensionView.visibility = View.INVISIBLE
            pensionBtn.setTextColor(Color.parseColor("#434343"))
            pensionBtn.setTypeface(null, Typeface.NORMAL)

        }
        overseasBtn.setOnClickListener {
            domesticView.visibility = View.INVISIBLE
            domesticBtn.setTextColor(Color.parseColor("#434343"))
            domesticBtn.setTypeface(null, Typeface.NORMAL)

            overseasView.visibility = View.VISIBLE
            overseasBtn.setTextColor(Color.parseColor("#1F1F1F"))
            overseasBtn.setTypeface(null, Typeface.BOLD)

            pensionView.visibility = View.INVISIBLE
            pensionBtn.setTextColor(Color.parseColor("#434343"))
            pensionBtn.setTypeface(null, Typeface.NORMAL)
        }
        pensionBtn.setOnClickListener {
            domesticView.visibility = View.INVISIBLE
            domesticBtn.setTextColor(Color.parseColor("#434343"))
            domesticBtn.setTypeface(null, Typeface.NORMAL)

            overseasView.visibility = View.INVISIBLE
            overseasBtn.setTextColor(Color.parseColor("#434343"))
            overseasBtn.setTypeface(null, Typeface.NORMAL)

            pensionView.visibility = View.VISIBLE
            pensionBtn.setTextColor(Color.parseColor("#1F1F1F"))
            pensionBtn.setTypeface(null, Typeface.BOLD)
        }

    }



}
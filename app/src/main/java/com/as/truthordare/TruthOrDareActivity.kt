package com.`as`.truthordare

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class TruthOrDareActivity : AppCompatActivity() {

   lateinit var restartImageView: ImageView
   lateinit var mainContent: TextView
   private var url: String? = null
   lateinit var mainCardView: CardView
   lateinit var progressBar: ProgressBar
   lateinit var abortOrDoneBtn: LinearLayout
   lateinit var abort: Button
   lateinit var done: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truth_or_dare)
        val headingOfCardView: TextView = findViewById(R.id.headingCard)
        restartImageView = findViewById(R.id.restart_view)
        mainContent = findViewById(R.id.contentTV)
        mainCardView = findViewById(R.id.cardView)
        progressBar = findViewById(R.id.progressBar)
        abortOrDoneBtn = findViewById(R.id.abortAndDoneBtnHolder)
        abort =  findViewById(R.id.abortButton)
        done = findViewById(R.id.doneButton)


        //getting flag to check either truth is pressed or dare is pressed
        val f = intent.getStringExtra("flag")

        //setting urls accordingly
        if (f == "1") {
            url = "https://api.truthordarebot.xyz/v1/truth"
            makeRequest()
        } else if (f == "2") {
            headingOfCardView.setText("Dare")
            url = "https://api.truthordarebot.xyz/v1/dare"
            makeRequest()
        }



        abort.setOnClickListener {
            mainContent.setText("The Player Lost!")
            mainContent.setTextColor(Color.RED)
            restartImageView.visibility = View.VISIBLE
            abort.isEnabled=false
            done.isEnabled=false
        }

        done.setOnClickListener(){
            mainContent.setText("The Player Won!")
            mainContent.setTextColor(Color.parseColor("#4AA02C"))
            restartImageView.visibility = View.VISIBLE
            abort.isEnabled=false
            done.isEnabled=false
        }

        restartImageView.setOnClickListener(){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun makeRequest() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response: JSONObject -> parseResponse(response) },
            { _: VolleyError? -> })
        queue.add(jsonObjectRequest)
    }

    private fun parseResponse(response: JSONObject) {
        try {
            val question: String = response.getString("question")
            mainContent.text = question
            mainCardView.visibility = View.VISIBLE
            abortOrDoneBtn.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }
}
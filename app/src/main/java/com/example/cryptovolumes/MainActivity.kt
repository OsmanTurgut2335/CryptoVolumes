package com.example.cryptovolumes

import CryptoAPI
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptovolumes.ui.theme.CryptoVolumesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val BASE_URL = "https://api.coingecko.com/api/v3/"

    private lateinit var cryptoAPI: CryptoAPI


    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptoAdapter: CryptoAdapter
    private var timerRunning = false
    private var timeLeftInMillis: Long = 10000 // Initial time in milliseconds
    private val maxTimeInMillis: Long = 10000 // Maximum time in milliseconds (60 seconds)
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromAPI()

        findViewById<Button>(R.id.button)
            .setOnClickListener {
             startTimer()
                fetchDataFromAPI()
            }
        startTimer()



    }


    private fun fetchDataFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/") // Replace with the base URL of the API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cryptoAPI = retrofit.create(CryptoAPI::class.java)

        val call = cryptoAPI.getCryptoVolumeData()
        call.enqueue(object : Callback<List<CryptoVolumeData>> {
            override fun onResponse(
                call: Call<List<CryptoVolumeData>>,
                response: Response<List<CryptoVolumeData>>
            ) {
                if (response.isSuccessful) {
                    val cryptoDataList = response.body() ?: emptyList()
                    displayDataInRecyclerView(cryptoDataList)
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<List<CryptoVolumeData>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun displayDataInRecyclerView(cryptoDataList: List<CryptoVolumeData>) {
        val sortedlist = cryptoDataList.sortedByDescending { it.market_cap_change_percentage_24h }
        cryptoAdapter = CryptoAdapter(sortedlist)
        recyclerView.adapter = cryptoAdapter
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        findViewById<Button>(R.id.button).text = "Refresh Data ("+ seconds.toString()+")"

        //timerTextView.text = seconds.toString() // Update the TextView directly

        findViewById<Button>(R.id.button).isEnabled = !timerRunning
    }

    private fun startTimer(){
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                // Reset the timer to 60 seconds
                timeLeftInMillis = maxTimeInMillis
                timerRunning=false
               updateTimer()
                
               // startTimer()

            }
        }.start()

        timerRunning = true
        updateTimer()
    }

}





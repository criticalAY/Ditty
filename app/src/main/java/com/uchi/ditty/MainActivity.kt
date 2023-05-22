package com.uchi.ditty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uchi.ditty.recyclerView.VideoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var recyclerView: RecyclerView
    private lateinit var shortsAdapter: VideoAdapter
    private var page: Int = 0
    private val limit: Int = 5
    private var isLoading: Boolean = false
    private lateinit var dummyLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.shorts_assignment)
        dummyLayout = findViewById(R.id.dummy_icons)

        recyclerView = findViewById(R.id.recycler_view_playlist)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = layoutManager
        shortsAdapter = VideoAdapter()
        recyclerView.adapter = shortsAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        dummyLayout.setOnClickListener {
            Toast.makeText(this, getString(R.string.dummy_navigation_icons), Toast.LENGTH_SHORT).show()
        }

        fetchVideoList()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPositions(null).maxOrNull()
                if (!isLoading && lastVisibleItemPosition != null && lastVisibleItemPosition >= totalItemCount - 1) {
                    fetchNextVideoList()
                }
            }
        })
    }

    private fun fetchVideoList() {
        page = 0
        isLoading = true

        val url = "https://internship-service.onrender.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(InterfaceAPI::class.java)

        apiService.getVideos(page, limit).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val videoList = apiResponse?.data?.posts
                    shortsAdapter.setVideoList(videoList!!)
                } else {
                    Toast.makeText(this@MainActivity, "Error retrieving API data", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                isLoading = false
                Log.e(TAG,"Error fetching the API")
            }
        })
    }

    private fun fetchNextVideoList() {
        if (isLoading) return
        isLoading = true
        val url = "https://internship-service.onrender.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(InterfaceAPI::class.java)

        apiService.getVideos(page, limit).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val videoList = apiResponse?.data?.posts
                    shortsAdapter.setVideoList(videoList!!)

                    page++
                } else {
                    Log.e(TAG,"Error fetching the API")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                isLoading = false // Reset the loading flag
                Log.e(TAG,"Error fetching the API")
            }
        })
    }

}

package com.example.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp5.api.Offre
import com.example.tp5.api.RestApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var reView : RecyclerView
    private lateinit var mAdapter : OffresListAdapter
    private lateinit var empty : CardView
    private lateinit var offreList : MutableList<Offre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reView = findViewById(R.id.listOffres)
        reView.layoutManager = LinearLayoutManager(this)
        empty = findViewById(R.id.empty)
        offreList = ArrayList()
        mAdapter = OffresListAdapter(this, offreList,R.layout.offre)
        reView.adapter = mAdapter

        fetchOffres()
    }

    fun fetchOffres(){
        val apiService= RestApiService()
        val call = apiService.fetchOffres()
        call.enqueue(
            object : Callback<List<Offre>> {
                override fun onFailure(call: Call<List<Offre>>, t: Throwable) {
                    reView.isVisible = false
                    empty.isVisible = true
                }

                override fun onResponse(call: Call<List<Offre>>, response: Response<List<Offre>>) {
                    if (response.isSuccessful) {
                        val offres = response.body()
                        if (offres != null) {
                            offreList.addAll(offres)
                            mAdapter.notifyDataSetChanged()
                            reView.adapter = mAdapter
                            if (offreList.isEmpty()) {
                                empty.visibility = CardView.VISIBLE
                                reView.visibility = RecyclerView.GONE
                            } else {
                                empty.visibility = CardView.GONE
                                reView.visibility = RecyclerView.VISIBLE
                            }
                        }
                    }
                }
            }
        )

    }
}
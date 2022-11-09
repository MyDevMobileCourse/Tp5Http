package com.example.tp5.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    fun fetchOffres(): Call<List<Offre>> {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        return retrofit.getOffres();
    }

    fun addOffre(offre: Offre, onResult: (Offre?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addOffre(offre).enqueue(
            object : Callback<Offre> {
                override fun onFailure(call: Call<Offre>, t: Throwable) {
                    println("It went bad")
                    onResult(null)
                }
                override fun onResponse( call: Call<Offre>, response: Response<Offre>) {
                    val addedUser = response.body()
                    println("It went good")
                    onResult(addedUser)
                }
            }
        )
    }

    fun deleteOffre(id: Int): Call<Boolean> {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        return retrofit.deleteOffre(id);
    }
}
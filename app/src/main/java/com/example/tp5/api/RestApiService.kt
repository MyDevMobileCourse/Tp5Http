package com.example.tp5.api

import retrofit2.Call

class RestApiService {
    fun fetchOffres(): Call<List<Offre>> {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        return retrofit.getOffres();
    }

    fun addOffre(offre: Offre): Call<Offre> {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        return retrofit.addOffre(offre);
    }

    fun deleteOffre(id: Int): Call<Boolean> {
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        return retrofit.deleteOffre(id);
    }
}
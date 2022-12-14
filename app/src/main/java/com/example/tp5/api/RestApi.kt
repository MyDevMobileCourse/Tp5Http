package com.example.tp5.api

import retrofit2.Call
import retrofit2.http.*

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("offres")
    fun addOffre(@Body offer: Offre): Call<Offre>
    @GET("Offres")
    fun getOffres():Call<List<Offre>>
    @DELETE("Offres/{id}")
    fun deleteOffre(@Path("id") id: Int): Call<Boolean>
    @PUT("Offres/{id}")
    fun updateOffre(@Path("id") id: Int, @Body offer: Offre): Call<Offre>
}
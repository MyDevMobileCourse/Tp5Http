package com.example.tp5.api

import com.google.gson.annotations.SerializedName

data class Offre(
    @SerializedName("code") val code: Long?,
    @SerializedName("intitulé") var intitulé: String?,
    @SerializedName("specialité") var specialité: String?,
    @SerializedName("société") var société: String?,
    @SerializedName("nbpostes") var nbpostes: Int?,
    @SerializedName("pays") var pays: String?,
)




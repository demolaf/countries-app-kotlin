package com.aob.android.countriesapp

import com.google.gson.annotations.SerializedName

// data class holding data from json file using the JSONObject name in @Serialized()
// and mapping (what we need) to our data class Country variables in constructor
data class Country(
    @SerializedName("name") val countryName: String?, @SerializedName("capital") val
    capital: String?, @SerializedName("flagPNG") val flag: String?
)
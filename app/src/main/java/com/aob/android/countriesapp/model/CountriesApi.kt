package com.aob.android.countriesapp.model

import com.aob.android.countriesapp.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {

    // GET annotation, GET(Endpoint)
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>

}
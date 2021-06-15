package com.aob.android.countriesapp.di

import com.aob.android.countriesapp.model.CountriesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// We need to annotate this class as @module using Dagger
// this will tell Dagger that this is a class that is a Module
// and will provide the Api's/Functions to the classes that need it

@Module
class ApiModule {

    private val BASE_URL = "https://raw.githubusercontent.com"

    // @Provides tells Dagger that in this module this function needs to be
    // provided
    @Provides
    fun provideCountriesApi(): CountriesApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

}
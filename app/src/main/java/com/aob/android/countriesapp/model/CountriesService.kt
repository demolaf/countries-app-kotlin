package com.aob.android.countriesapp.model

import com.aob.android.countriesapp.Country
import com.aob.android.countriesapp.di.DaggerApiComponent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CountriesService {

    // Base URL for multiple endpoints if there is
    // BASE URL was moved to class ApiModule for dependency injection
    /*private val BASE_URL = "https://raw.githubusercontent.com"*/

    // @Inject to let dagger know to inject it, when we do the init in the init{}
    // block we no longer need to instantiate api directly from the service but instead
    // using the ApiComponent created by Dagger, the @Inject here is used by Dagger in
    // the init{} block DaggerApiComponent but we can't see the workings
    @Inject
    lateinit var api: CountriesApi

    // we move the api instantiation from the service to another class called ApiModule
    // for dependency injection

    init {
        // getting the api from the ApiComponent without instantiating it, Dagger
        // creates and holds the instance we just get it.
        DaggerApiComponent.create().inject(this)

        // 1.Retrofit.Builder() creates the framework for retrofit that helps us get the
        // the information from the backend
        // 2.baseUrl() sets/holds the base url for multiple endpoints
        // 3.addConverterFactory(GsonConverterFactory.create()) transforms
        // json object (received from backend) to objects that we'll use in our code
        // (kotlin code (check this again o!) (deserialization)).
        // 4.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) is used to transform
        // 'Country' data class to a variable which is going to be an Observable similar
        // to our countries: MutableLiveData (LiveData) in ViewModel, this is so something
        // can subscribe to the variable created by RxJava and get notification when it
        // changes.
        // 5.build() to build it
        // 6.create(CountriesApi::class.java) gives it the type of information it can return
        // meaning in our case Retrofit will be able to return through a method
        // (getCountries()) in CountriesApi, a list (List<Country>) of countries wrapped
        // around a class Single<> which means it's an observable of type "Single" that
        // emits one variable then closes
        /*api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)*/

    }


    // To get list of Countries from Api we defined
    // Single is an observable that emits only one value and closes
    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }

}
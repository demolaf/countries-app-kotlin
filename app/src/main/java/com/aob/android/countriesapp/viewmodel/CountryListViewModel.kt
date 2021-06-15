package com.aob.android.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aob.android.countriesapp.Country
import com.aob.android.countriesapp.model.CountriesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountryListViewModel : ViewModel() {

    // service to call endpoint
    private val countriesService = CountriesService()

    // Since this ViewModel is using RxJava to get the information from the service
    // when the viewModel is closed/not-in-use we need to close the connection
    // that's what "CompositeDisposable() is used for.
    // used to close connection
    private val disposable = CompositeDisposable()

    // Hold a List of countries using Country data class
    val countries = MutableLiveData<List<Country>>()

    // To notify error loading data from backend
    val countryLoadError = MutableLiveData<Boolean>()

    // To notify loading from backend
    val loading = MutableLiveData<Boolean>()

    // Using RxJava and RxAndroid
    // 1. countriesService.getCountries() is used to get list of countries of type Single
    // observable
    // 2. subscribeOn() is used to tell android which thread we want to execute the task
    // because we're getting from backend we can't use mainThread but instead
    // asynchronously using Schedulers.newThread() on a BACKGROUND THREAD
    // 3. ObserveOn() is used to tell android where we're going to observe the result
    // of the async task/task, in this case AndroidSchedulers.mainThread() i.e.
    // on the MAIN THREAD.
    // 4. SubscribeWith() is where we define what we want to do when we get the info
    // 5. DisposableSingleObserver<>() will define the functionality we will do when
    // we receive the info.
    fun refresh() {
        fetchCountries()
        disposable.add(countriesService.getCountries().subscribeOn(Schedulers.newThread()).observeOn
            (AndroidSchedulers.mainThread()).subscribeWith(object:
            DisposableSingleObserver<List<Country>>() {
            override fun onSuccess(t: List<Country>) {
                // setting info gotten from the backend to our countries list
                countries.value = t
                // setting this to false because no Error
                countryLoadError.value = false
                // setting this to false because it's done loading
                loading.value = false
            }

            override fun onError(e: Throwable) {
                // set this to true onError from backend
                countryLoadError.value = true
                // set this to false onError from backend
                loading.value = false
            }
        }))
    }

    override fun onCleared() {
        super.onCleared()
        // when ViewModel not in use clear the task to avoid memory leak
        disposable.clear()
    }

    //
    private fun fetchCountries() {

        // Using data from backend
        // We have to notify subscribers listening to our liveData loading
        // that loading has started true/false, here true because it has started
        loading.value = true



        /*// mockData to hold list of countries
        val mockData = listOf(
                Country("CountryA"),
                Country("CountryB"),
                Country("CountryC"),
                Country("CountryD"),
                Country("CountryE"),
                Country("CountryF"),
                Country("CountryG"),
                Country("CountryH"),
                Country("CountryI"),
                Country("CountryJ"),
        )

        // Setting this to false because we're using mock data
        countryLoadError.value = false

        // Since we're not loading anything from backend
        loading.value = false

        // hooking up mockData with Mutable LiveData list of countries
        countries.value = mockData*/

    }
}
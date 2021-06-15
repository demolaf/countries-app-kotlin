package com.aob.android.countriesapp.di

import com.aob.android.countriesapp.model.CountriesService
import dagger.Component

// @Component will let dagger know that this is a component
// We need to pass in a list of modules (@module) that we created into the component
@Component(modules = [ApiModule::class])
interface ApiComponent {

    // this function will help dagger, inject the right components from ApiModule
    // into countriesService class
    fun inject(service: CountriesService)

}
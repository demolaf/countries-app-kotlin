package com.aob.android.countriesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aob.android.countriesapp.R
import com.aob.android.countriesapp.databinding.FragmentCountryListViewBinding
import com.aob.android.countriesapp.viewmodel.CountryListViewModel


class CountryListViewFragment : Fragment() {

    lateinit var countryListViewModel: CountryListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    lateinit var binding: FragmentCountryListViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        // 'DataBindingUtil' Utility class to create ViewDataBinding from layouts.
        // Inflate view and obtain an instance of the binding class.
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_country_list_view,
            container,
            false
        )

        // initializing/instantiating ListViewModel
        countryListViewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        countryListViewModel.refresh()

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        // Refresh list of country when swiped down to refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            countryListViewModel.refresh()
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {

        // Observing countries<List<Country>> LiveData
        countryListViewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            // 'let' means if not null do {}
            countries?.let {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        // Observing countryLoadError<Boolean> LiveData
        countryListViewModel.countryLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                // setting the 'android:visibility' property of TextView
                binding.listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        // Observing loading<Boolean> LiveData
        countryListViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.loadingProgressBar.visibility = if(it) View.VISIBLE else View.GONE

                // Hiding all views when progress bar is showing if loading is true
                if(it) {
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }

        })

    }
}
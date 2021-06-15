package com.aob.android.countriesapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aob.android.countriesapp.Country
import com.aob.android.countriesapp.R
import com.aob.android.countriesapp.util.getProgressDrawable
import com.aob.android.countriesapp.util.loadImage
import com.aob.android.countriesapp.databinding.ItemCountryBinding

// RecyclerView.Adapter requires an argument class of type RecyclerView.ViewHolder
class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    // when this is called a new list of countries is created if the list changes
    // and this will be used by our ListViewFragment
    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        // This is used to tell the android system/view that we have new list of countries and to recreate the list
        notifyDataSetChanged()
    }

    // To create the ViewHolder class we need a View which is going to hold
    // the item_country.xml view created
    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: ItemCountryBinding = ItemCountryBinding.bind(view)

        // getting the textView 'text' property from item_country.xml
        private val countryName = binding.countryNameTextView

        //
        private val imageView = binding.imageView

        //
        private val countryCapital = binding.countryCapitalTextView

        //
        private val progressDrawable = getProgressDrawable(view.context)

        // To bind the views to the layout (RecyclerView)
        fun bind(country: Country) {
            //TODO: I think this can be replaced using data binding by accessing the data class directly from the xml layout.

            // setting the 'text' property of the textView to countryName property in data class 'Country'.
            countryName.text = country.countryName

            // setting the 'text' property of the textView to countryCapital property in
            // data class 'Country'.
            countryCapital.text = country.capital

            // loading the image using and extended function with Glide in Util file
            imageView.loadImage(country.flag, progressDrawable)

        }



    }

    // This method creates/uses the class CountryViewHolder (The Recycler View is the one that takes the CountryViewHolder class when extended as seen in the class declaration).
    // This is a shorter version of the function below do not uncomment
    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_country, parent, false)
    )*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder{
        // Return the view from the CountryViewHolder
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        // To bind the countries to the layout (RecyclerView)
        holder.bind(countries[position])
    }

    // returning the size/length/count of list of countries
    override fun getItemCount(): Int {
        return countries.size
    }
}
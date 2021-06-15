package com.aob.android.countriesapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aob.android.countriesapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// a spinner progress that can be used in a view to indicate loading
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

// You can extend Views or Classes in kotlin using something called extensions research this
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {

    // Using glide to load the image from the URL and use a progressDrawable as loader
    // RequestOptions() is from glide
    // placeholder() takes in a drawable to be displayed
    // error() takes in a drawable to be displayed if image/uri not loaded
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    // with() takes in the context of the view to do the work
    // setDefaultRequestOptions() takes in RequestOptions created above
    // load() load the Uri of the image
    // into() the view to load the image
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

}
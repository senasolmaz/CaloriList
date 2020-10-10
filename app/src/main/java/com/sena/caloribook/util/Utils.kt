package com.sena.caloribook.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sena.caloribook.R

fun ImageView.gorselIndir(url: String?, placeholder: CircularProgressDrawable) {

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

//imageler yklenene kadar progress koyar
fun placeHolderYap(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage") //bunu koyunca bu fonku xml tarafında kullanabiliriz
fun downloadImage(imageView: ImageView,url: String?) {
    imageView.gorselIndir(url, placeHolderYap(imageView.context))
}
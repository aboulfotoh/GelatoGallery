package com.gelato.gelatogallery.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gelato.gelatogallery.R

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(imgView)
    }
}


@BindingAdapter("adapter", "listData")
fun bindRecyclerView(view: RecyclerView, adapter: Any, data: List<Any>?) {
    var recyclerViewAdapter = adapter as androidx.recyclerview.widget.ListAdapter<*, *>
    recyclerViewAdapter.submitList(data as List<Nothing>?)
    recyclerViewAdapter.notifyDataSetChanged()
    if (view.adapter == null) {
        //view.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        view.adapter = recyclerViewAdapter
    } else {
        view.adapter = recyclerViewAdapter
        //adapter.transactions = historyAdapter?.transactions
        view.adapter?.notifyDataSetChanged()
    }
}
package com.incursio.randomusers.users

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incursio.randomusers.repository.remote.model.User

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<User>) {
    (recyclerView.adapter as UsersAdapter).submitList(items)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}
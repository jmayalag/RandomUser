package com.incursio.randomusers.users

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.incursio.randomusers.R
import com.incursio.randomusers.repository.remote.model.User
import com.squareup.picasso.Picasso

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<User>) {
    (recyclerView.adapter as UsersAdapter).submitList(items)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Picasso.get()
        .load(url)
//        .placeholder(R.drawable.user_placeholder)
        .error(R.drawable.error_image)
        .into(view)
}
package com.incursio.randomusers.users

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.incursio.randomusers.repository.remote.model.User

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<User>) {
    (recyclerView.adapter as UsersAdapter).submitList(items)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (url.isNullOrBlank()) return
    Glide.with(view.context)
        .load(url)
        .transition(withCrossFade())
        .into(view)
}

@set:BindingAdapter("invisible")
var View.invisible
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

@set:BindingAdapter("gone")
var View.gone
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }
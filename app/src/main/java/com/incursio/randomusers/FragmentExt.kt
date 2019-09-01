package com.incursio.randomusers

import androidx.fragment.app.Fragment

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val randomUserApp = (requireContext().applicationContext as RandomUserApp)
    return ViewModelFactory(randomUserApp.usersRepository)
}
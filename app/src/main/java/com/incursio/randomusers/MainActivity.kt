package com.incursio.randomusers

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber

class MainActivity : AppCompatActivity(), UserDetail.OnUserDetailInteractionListener,
    UserList.OnUserListInteractionListener {
    override fun onUserListInteraction(uri: Uri) {
        Timber.d(uri.toString())
    }

    override fun onUserDetailInteraction(uri: Uri) {
        Timber.d(uri.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

package com.incursio.randomusers.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.incursio.randomusers.R
import com.incursio.randomusers.databinding.FragmentUserDetailBinding
import com.incursio.randomusers.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetail : Fragment() {
    private val args: UserDetailArgs by navArgs()

    private lateinit var viewDataBinding: FragmentUserDetailBinding

    private val viewModel by viewModels<UserDetailViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
            }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        viewModel.start(args.userId)
    }
}

package com.incursio.randomusers.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.incursio.randomusers.databinding.FragmentUserDetailBinding
import com.incursio.randomusers.getViewModelFactory

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
                lifecycleOwner = viewLifecycleOwner
            }

//        setupToolbar()
        viewModel.start(args.userId)

        return viewDataBinding.root
    }

    private fun setupToolbar() {
//        val navController = findNavController()
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        (activity as AppCompatActivity).apply {
//            setSupportActionBar(viewDataBinding.toolbar)
//        }
//        viewDataBinding.apply {
//            toolbarLayout.setupWithNavController(toolbar, navController, appBarConfiguration)
//        }
    }
}

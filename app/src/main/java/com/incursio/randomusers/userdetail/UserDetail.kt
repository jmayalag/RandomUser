package com.incursio.randomusers.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.incursio.randomusers.R
import com.incursio.randomusers.databinding.FragmentUserDetailBinding
import com.incursio.randomusers.getViewModelFactory
import timber.log.Timber

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

        setupToolbar()
        viewModel.start(args.userId)

        return viewDataBinding.root
    }

    private fun setupToolbar() {
        viewDataBinding.toolbar.apply {
            setNavigationOnClickListener { v -> v.findNavController().navigateUp() }

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_add_contact -> {
                        addUserAsContact()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun addUserAsContact() {
        // TODO: Add user as contact intent
        val user = viewModel.user.value ?: return
        Timber.d("Adding as user ${user.fullName}")
    }
}

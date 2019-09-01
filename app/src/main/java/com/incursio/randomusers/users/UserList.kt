package com.incursio.randomusers.users

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.incursio.randomusers.R
import com.incursio.randomusers.databinding.FragmentUserListBinding
import com.incursio.randomusers.getViewModelFactory
import timber.log.Timber

class UserList : Fragment() {

    private val viewModel by viewModels<UsersViewModel> { getViewModelFactory() }

    private lateinit var viewDataBinding: FragmentUserListBinding

    private lateinit var listAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentUserListBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }

        setupToolbar()

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        setupListAdapter()
        setupNavigation()

        viewModel.loadUsers(true)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(viewDataBinding.toolbar)
            title = null
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_list, menu)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.vm
            ?: return Timber.w("ViewModel not initialized when attempting to set up adapter.")

        listAdapter = UsersAdapter(viewModel)
        viewDataBinding.userList.apply {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setupNavigation() {
        viewModel.openUserEvent.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                Timber.d("Will show user ${it.fullName}")
                openUserDetail(user.idValue)
                viewModel.openedUser()
            }
        })
    }

    private fun openUserDetail(userId: String) {
        val action = UserListDirections.actionUserListToUserDetail(userId)
        findNavController().navigate(action)
    }
}


package com.terranullius.bhoomicabs.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.terranullius.bhoomicabs.ui.viewmodels.BaseViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseFragment() : Fragment() {

    private lateinit var viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
    }

    abstract fun initViewModel(): BaseViewModel

    abstract fun onNavigate(navigationEvent: NavigationEvent)

    override fun onStart() {
        super.onStart()
        registerObservers()
    }

    private fun registerObservers() {
        lifecycleScope.launchWhenStarted {
            if (::viewModel.isInitialized) viewModel.navigationEventState.collect {
                it.getContentIfNotHandled()?.let { navigationEvent ->
                    onNavigate(navigationEvent)
                }
            }
        }
    }
}
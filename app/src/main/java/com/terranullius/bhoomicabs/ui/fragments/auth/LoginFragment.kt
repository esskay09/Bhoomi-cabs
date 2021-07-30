package com.terranullius.bhoomicabs.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.ui.platform.ComposeView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.ui.composables.MyApp
import com.terranullius.bhoomicabs.ui.fragments.auth.composables.LoginScreen
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.BaseViewModel
import com.terranullius.bhoomicabs.ui.viewmodels.AuthViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment() {

    private val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.navGraph_auth)

    override fun initViewModel(): BaseViewModel = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
        if (navigationEvent is NavigationEvent.LoginToOtp){
            findNavController().navigate(R.id.action_loginFragment_to_OTPConfirmation)
        }
    }

    override fun onStart() {
        super.onStart()
        registerObservers()
    }

    private fun registerObservers() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        view.apply {
            setContent {
                MyApp {
                        LoginScreen(viewModel)
                    }
            }
        }
        return view
    }
}
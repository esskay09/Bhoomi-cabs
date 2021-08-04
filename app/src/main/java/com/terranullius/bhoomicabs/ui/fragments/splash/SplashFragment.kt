package com.terranullius.bhoomicabs.ui.fragments.splash

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.other.Constants.PREFS_DIR
import com.terranullius.bhoomicabs.other.Constants.PREF_NUMBER
import com.terranullius.bhoomicabs.other.Constants.PREF_VERIFIED
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.AuthViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.navGraph_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private var isVerified = false

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
       when(navigationEvent){
           is NavigationEvent.SplashToLogin -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
           is NavigationEvent.SplashToBookings -> findNavController().navigate(R.id.action_splashFragment_to_bookingsFragment)
           else -> {}
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(PREFS_DIR, Context.MODE_PRIVATE)
         isVerified = prefs.getBoolean(PREF_VERIFIED, false)

        if (isVerified){
            val userNumber = prefs.getLong(PREF_NUMBER, 9334805466)
            viewModel.setNumber(userNumber)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(3000L)
           try {
               if (!isVerified) viewModel.navigate(NavigationEvent.SplashToLogin)
               else viewModel.navigate(NavigationEvent.SplashToBookings)
           } catch (e: Exception){
               Log.d("fuck", "splash: ${e.message}")
           }
        }

    }
}


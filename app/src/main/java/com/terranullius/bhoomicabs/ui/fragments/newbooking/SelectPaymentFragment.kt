package com.terranullius.bhoomicabs.ui.fragments.newbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.ui.composables.MyApp
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.SelectPaymentScreen
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectPaymentFragment : BaseFragment() {
    private val viewModel by hiltNavGraphViewModels<NewBookingViewModel>(R.id.navGraph_new_booking)

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
        when(navigationEvent){
            is NavigationEvent.SelectPaymentToBookingFinished -> findNavController().navigate(R.id.action_selectPaymentFragment_to_bookingFinishedFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ComposeView(requireContext())
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.apply {
            setContent {
                MyApp() {
                    SelectPaymentScreen(Modifier.fillMaxSize(), viewModel = viewModel)
                }
            }
        }
        return view
    }
}
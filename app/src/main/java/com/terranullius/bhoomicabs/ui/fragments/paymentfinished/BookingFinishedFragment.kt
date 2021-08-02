package com.terranullius.bhoomicabs.ui.fragments.paymentfinished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.ui.composables.MyApp
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.fragments.paymentfinished.composables.BookingFinishedScreen
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent

class BookingFinishedFragment: BaseFragment() {
    private val viewModel by hiltNavGraphViewModels<NewBookingViewModel>(R.id.navGraph_new_booking)

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
      when(navigationEvent){
          is NavigationEvent.BoookingFinishedToBookings -> findNavController().navigate(R.id.action_bookingFinishedFragment_to_navGraph_bookings)
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
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.apply {
            setContent {
                MyApp {
                  Surface(color = Color.White)  {
                        BookingFinishedScreen(
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
        return view
    }
}
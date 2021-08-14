package com.terranullius.bhoomicabs.ui.fragments.bookings

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
import com.terranullius.bhoomicabs.ui.fragments.bookings.composables.BookingsScreen
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.BookingsViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookingsFragment: BaseFragment(){
    private val viewModel by hiltNavGraphViewModels<BookingsViewModel>(R.id.navGraph_bookings)

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
        when(navigationEvent){
            is NavigationEvent.BookingsToBookingDetail -> {
                findNavController().navigate(R.id.action_bookingsFragment_to_bookingDetailFragment)
            }
            is NavigationEvent.BookingsToNewBooking ->{
                findNavController().navigate(R.id.action_bookingsFragment_to_newBookingFragment)
            }
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
                MyApp{
                    BookingsScreen(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
                }
            }
        }
        return view
    }
}
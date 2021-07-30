package com.terranullius.bhoomicabs.ui.fragments.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.data.Booking
import com.terranullius.bhoomicabs.ui.composables.MyApp
import com.terranullius.bhoomicabs.ui.fragments.bookings.composables.BookingDetailScreen
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.BookingsViewModel
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BookingDetailFragment: BaseFragment() {

    private val viewModel by hiltNavGraphViewModels<BookingsViewModel>(R.id.navGraph_bookings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerObservers()
    }

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
        TODO("Not yet implemented")
    }

    private var selectedBooking: Booking? = null

    @OptIn(InternalCoroutinesApi::class)
    private fun registerObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.selectedBookingStateFlow.collect {
                selectedBooking = it
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
                selectedBooking?.let {
                    MyApp {
                            BookingDetailScreen(booking = it)
                        }
                }
            }
        }
        return view
    }
}
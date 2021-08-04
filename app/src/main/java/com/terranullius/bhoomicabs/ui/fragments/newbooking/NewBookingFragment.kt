package com.terranullius.bhoomicabs.ui.fragments.newbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.terranullius.bhoomicabs.R
import com.terranullius.bhoomicabs.ui.composables.MyApp
import com.terranullius.bhoomicabs.ui.fragments.newbooking.composables.NewBookingScreen
import com.terranullius.bhoomicabs.ui.fragments.BaseFragment
import com.terranullius.bhoomicabs.ui.viewmodels.NewBookingViewModel
import com.terranullius.bhoomicabs.util.DialogShowEvent
import com.terranullius.bhoomicabs.util.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class NewBookingFragment: BaseFragment(){
    private val viewModel by hiltNavGraphViewModels<NewBookingViewModel>(R.id.navGraph_new_booking)

    override fun initViewModel() = viewModel

    override fun onNavigate(navigationEvent: NavigationEvent) {
        when(navigationEvent){
            is NavigationEvent.NewBookingToSelectCar -> navigateToSelectCarFragment()
        }
    }

    private fun navigateToSelectCarFragment() {
        findNavController().navigate(R.id.action_newBookingFragment_to_selectCarFragment)
    }

    override fun onStart() {
        super.onStart()
        registerObservers()
    }


    private fun registerObservers() {
        lifecycleScope.launchWhenCreated{
            viewModel.showDialogEvent.collect{
                when(it.getContentIfNotHandled()){
                    is DialogShowEvent.ShowDatePicker -> {
                        showDatePicker(viewModel.oneWay.value)
                    }
                    is DialogShowEvent.ShowTimePicker -> {
                        showTimePicker()
                    }
                }
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
                MyApp {
                NewBookingScreen(Modifier.fillMaxSize(), viewModel = viewModel)
                    }
            }
        }
        return view
    }

    private fun showDatePicker(oneWay: Boolean) {

        if (oneWay) {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now())
                        .build()
                )
                .build()

            datePicker.addOnPositiveButtonClickListener {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                viewModel.setStartDate(date)
            }
            datePicker.show(parentFragmentManager, "date")
        } else {
            val datePicker = MaterialDatePicker.Builder
                .dateRangePicker()
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now())
                        .build()
                )
                .build()

            datePicker.addOnPositiveButtonClickListener {
                val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.first)
                val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.second)

                viewModel.setStartDate(startDate)
                viewModel.setEndDate(endDate)
            }
            datePicker.show(parentFragmentManager, "date")
        }
    }

    private fun showTimePicker() {

        val timePicker = MaterialTimePicker.Builder()
            .setTitleText("Pickup time")
            .build()
        timePicker.addOnPositiveButtonClickListener {
            val time = (if (timePicker.hour < 10) "0" else "") +
                    "${timePicker.hour}" + ":" +
                    (if (timePicker.minute < 10) "0" else "") + "${timePicker.minute} hrs"

            viewModel.setPickupTime(time)
        }

        timePicker.addOnNegativeButtonClickListener {

            timePicker.dismiss()
        }

        timePicker.show(parentFragmentManager, "time-picker")
    }

}
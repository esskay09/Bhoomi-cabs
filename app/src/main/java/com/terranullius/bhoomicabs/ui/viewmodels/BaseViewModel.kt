package com.terranullius.bhoomicabs.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.terranullius.bhoomicabs.util.Event
import com.terranullius.bhoomicabs.util.NavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


abstract class BaseViewModel : ViewModel() {

    private val _navigationEventState: MutableStateFlow<Event<NavigationEvent>> =
        MutableStateFlow(Event(NavigationEvent.None))
    val navigationEventState: StateFlow<Event<NavigationEvent>>
        get() = _navigationEventState

    fun navigate(navigationEvent: NavigationEvent) {
        _navigationEventState.value = Event(navigationEvent)
    }


    companion object {
        @JvmStatic
        protected val _phoneNumberStateFlow = MutableStateFlow(0L)
        val phoneNumberStateFlow: MutableStateFlow<Long>
            get() = _phoneNumberStateFlow
    }
}
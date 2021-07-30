package com.terranullius.bhoomicabs.util

sealed class DialogShowEvent{
    object ShowDatePicker: DialogShowEvent()
    object ShowTimePicker: DialogShowEvent()
    object None: DialogShowEvent()
}

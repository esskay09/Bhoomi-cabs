package com.terranullius.bhoomicabs.util

enum class PaymentType(proportion: Float) {
    FULL(proportion = 1f), HALF(proportion = 0.5f), QUARTER(proportion = 0.25f), OTHER(0f)
}
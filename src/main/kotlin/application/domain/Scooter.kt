package com.example.application.domain

import com.example.application.domain.ScooterStatus.LOCKED

@JvmInline
value class ScooterId(val value: Int)

enum class ScooterStatus {
    RUNNING,
    LOCKED,
}

data class Scooter(
    val id: ScooterId,
    val status: ScooterStatus,
    val lastRider: UserId,
) {

    fun locked(): Scooter = copy(status = LOCKED)
}

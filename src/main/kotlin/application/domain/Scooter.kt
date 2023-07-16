package com.example.application.domain

import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING

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

    fun running(userId: UserId): Scooter = copy(status = RUNNING, lastRider = userId)
    fun locked(): Scooter = copy(status = LOCKED)
}

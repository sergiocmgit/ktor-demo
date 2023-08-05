package com.example.application.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
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

    fun running(userId: UserId): Either<ScooterInvalidStatus, Scooter> =
        if (status != LOCKED) {
            ScooterInvalidStatus.left()
        } else copy(status = RUNNING, lastRider = userId).right()

    fun locked(userId: UserId): Either<ScooterInvalidStatus, Scooter> =
        if (status != RUNNING || lastRider != userId) {
            ScooterInvalidStatus.left()
        } else copy(status = LOCKED).right()
}

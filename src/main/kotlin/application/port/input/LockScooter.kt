package com.example.application.port.input

import arrow.core.Either
import com.example.application.domain.LockScooterError

interface LockScooter {
    operator fun invoke(request: LockScooterRequest): Either<LockScooterError, ScooterLocked>
}

data class LockScooterRequest(
    val scooterId: Int,
    val userId: String,
)

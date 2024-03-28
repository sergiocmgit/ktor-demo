package com.example.application.port.input

import arrow.core.Either
import com.example.application.domain.LockScooterError
import com.example.application.domain.ScooterLocked

interface LockScooter {
    operator fun invoke(input: LockScooterInput): Either<LockScooterError, ScooterLocked>
}

data class LockScooterInput(
    val scooterId: Int,
    val userId: String,
)

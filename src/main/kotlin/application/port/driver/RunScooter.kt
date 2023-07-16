package com.example.application.port.driver

import arrow.core.Either
import com.example.application.domain.RunScooterError

interface RunScooter {

    operator fun invoke(request: RunScooterRequest): Either<RunScooterError, ScooterRunning>
}

data class RunScooterRequest(
    val scooterId: Int,
    val userId: String,
)

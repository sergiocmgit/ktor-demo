package com.example.application.port.input

import arrow.core.Either
import com.example.application.domain.RunScooterError
import com.example.application.domain.ScooterRunning

interface RunScooter {
    operator fun invoke(input: RunScooterInput): Either<RunScooterError, ScooterRunning>
}

data class RunScooterInput(
    val scooterId: Int,
    val userId: String,
)

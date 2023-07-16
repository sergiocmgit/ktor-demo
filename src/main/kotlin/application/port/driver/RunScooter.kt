package com.example.application.port.driver

interface RunScooter {

    operator fun invoke(request: RunScooterRequest): RunScooterResponse
}

data class RunScooterRequest(
    val scooterId: Int,
    val userId: String,
)

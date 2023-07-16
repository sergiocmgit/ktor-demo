package com.example.application.port.driver

interface LockScooter {

    operator fun invoke(request: LockScooterRequest): LockScooterResponse
}

data class LockScooterRequest(
    val scooterId: Int,
    val userId: String,
)

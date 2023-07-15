package com.example.application.port.driver

interface LockScooter {

    operator fun invoke(request: LockScooterRequest): LockScooterResponse
}

data class LockScooterRequest(
    val scooterId: Int,
    val userId: String,
)

sealed interface LockScooterResponse

data class ScooterLocked(val scooterId: Int) : LockScooterResponse
data class UserNotFound(val userId: String) : LockScooterResponse
data object UserStatusInvalid : LockScooterResponse
data class ScooterNotFound(val scooterId: Int) : LockScooterResponse

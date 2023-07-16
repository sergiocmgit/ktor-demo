package com.example.application.port.driver

sealed interface RunScooterResponse
sealed interface LockScooterResponse

data class ScooterRunning(val scooterId: Int) : RunScooterResponse
data class ScooterLocked(val scooterId: Int) : LockScooterResponse
data class UserNotFound(val userId: String) : RunScooterResponse, LockScooterResponse
data object UserStatusInvalid : RunScooterResponse, LockScooterResponse
data class ScooterNotFound(val scooterId: Int) : RunScooterResponse, LockScooterResponse
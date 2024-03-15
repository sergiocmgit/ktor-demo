package com.example.application.domain

sealed interface RunScooterError

sealed interface LockScooterError

data object ScooterInvalidStatus : RunScooterError, LockScooterError
data object UserInvalidStatus : RunScooterError, LockScooterError

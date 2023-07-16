package com.example.application.domain

sealed interface RunScooterError

data object UserNotFound : RunScooterError
data object ScooterNotFound : RunScooterError

data object ScooterInvalidStatus : RunScooterError
data object UserInvalidStatus : RunScooterError
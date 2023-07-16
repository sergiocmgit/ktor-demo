package com.example.application.domain

sealed interface DomainError

data object ScooterInvalidStatus : DomainError
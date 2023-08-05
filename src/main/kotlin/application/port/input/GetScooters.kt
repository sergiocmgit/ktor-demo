package com.example.application.port.input

import com.example.application.domain.ScooterStatus
import kotlinx.serialization.Serializable

interface GetScooters {
    operator fun invoke(): GetScootersResponse
}

@Serializable
data class GetScootersResponse(
    val scooters: List<ScooterResponse>,
)

@Serializable
data class ScooterResponse(
    val id: Int,
    val status: ScooterStatus,
    val lastRider: String,
)

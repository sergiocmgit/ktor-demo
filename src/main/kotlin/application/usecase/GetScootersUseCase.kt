package com.example.application.usecase

import com.example.application.port.input.GetScooters
import com.example.application.port.input.GetScootersResponse
import com.example.application.port.input.ScooterResponse
import com.example.application.port.output.ScooterRepository

// TODO: confirm if it is truly needed to hide the UseCase classes behind input interfaces
class GetScootersUseCase(
    private val scooterRepository: ScooterRepository,
) : GetScooters {
    override fun invoke(): GetScootersResponse =
        scooterRepository.findAll()
            .map { ScooterResponse(it.id.value, it.status, it.lastRider.value) }
            .let { GetScootersResponse(it) }
}

package com.example.application.usecase

import com.example.application.port.input.GetScooters
import com.example.application.port.input.GetScootersResponse
import com.example.application.port.input.ScooterResponse
import com.example.application.port.output.ScooterRepository

class GetScootersUseCase(
    private val scooterRepository: ScooterRepository,
) : GetScooters {

    override fun invoke(): GetScootersResponse =
        scooterRepository.findAll()
            .map { ScooterResponse(it.id.value, it.status, it.lastRider.value) }
            .let { GetScootersResponse(it) }
}

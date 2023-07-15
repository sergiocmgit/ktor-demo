package com.example.application.usecase

import com.example.application.port.driven.ScooterRepository
import com.example.application.port.driver.GetScooters
import com.example.application.port.driver.GetScootersResponse
import com.example.application.port.driver.ScooterResponse

class GetScootersUseCase(
    private val scooterRepository: ScooterRepository,
) : GetScooters {

    override fun invoke(): GetScootersResponse =
        scooterRepository.findAll()
            .map { ScooterResponse(it.id.value, it.status, it.lastRider.value) }
            .let { GetScootersResponse(it) }
}
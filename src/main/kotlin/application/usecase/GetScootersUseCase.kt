package com.example.application.usecase

import com.example.application.port.driven.ScooterFinder
import com.example.application.port.driver.GetScooters
import com.example.application.port.driver.GetScootersResponse
import com.example.application.port.driver.ScooterResponse

class GetScootersUseCase(
    private val scooterFinder: ScooterFinder,
) : GetScooters {

    override fun invoke(): GetScootersResponse =
        scooterFinder.findAll()
            .map { ScooterResponse(it.id.value, it.status, it.lastRider.value) }
            .let { GetScootersResponse(it) }
}
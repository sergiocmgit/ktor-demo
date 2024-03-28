package com.example.application.usecase

import com.example.application.domain.Scooter
import com.example.application.port.input.GetScooters
import com.example.application.port.output.ScooterRepository

// TODO: confirm if it is truly needed to hide the UseCase classes behind input interfaces
class GetScootersUseCase(
    private val scooterRepository: ScooterRepository,
) : GetScooters {
    override fun invoke(): List<Scooter> = scooterRepository.findAll()
}

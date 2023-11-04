package com.example.application.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.application.domain.RunScooterError
import com.example.application.domain.ScooterId
import com.example.application.domain.UserId
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterRequest
import com.example.application.port.input.ScooterRunning
import com.example.application.port.output.ScooterRepository
import com.example.application.port.output.UserRepository

class RunScooterUseCase(
    private val userRepository: UserRepository,
    private val scooterRepository: ScooterRepository,
) : RunScooter {
    override fun invoke(request: RunScooterRequest): Either<RunScooterError, ScooterRunning> =
        userRepository.find(UserId(request.userId))
            .flatMap { it.checkIsActive() }
            .flatMap { scooterRepository.find(ScooterId(request.scooterId)) }
            .flatMap { it.running(UserId(request.userId)) }
            .onRight { scooterRepository.update(it) }
            .map { ScooterRunning(it.id.value) }
}

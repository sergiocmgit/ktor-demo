package com.example.application.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.application.domain.RunScooterError
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterRunning
import com.example.application.domain.UserId
import com.example.application.domain.service.GetActiveUser
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterInput
import com.example.application.port.output.ScooterRepository

class RunScooterUseCase(
    private val getActiveUser: GetActiveUser,
    private val scooterRepository: ScooterRepository,
) : RunScooter {
    override fun invoke(input: RunScooterInput): Either<RunScooterError, ScooterRunning> =
        getActiveUser(UserId(input.userId))
            .map { scooterRepository.find(ScooterId(input.scooterId)) }
            .flatMap { it.running(UserId(input.userId)) }
            .onRight { scooterRepository.update(it) }
            .map { ScooterRunning(it.id.value) }
}

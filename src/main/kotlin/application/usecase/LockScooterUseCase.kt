package com.example.application.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.application.domain.LockScooterError
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterLocked
import com.example.application.domain.UserId
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterInput
import com.example.application.port.output.ScooterRepository
import com.example.application.port.output.UserRepository

class LockScooterUseCase(
    private val userRepository: UserRepository,
    private val scooterRepository: ScooterRepository,
) : LockScooter {
    override fun invoke(input: LockScooterInput): Either<LockScooterError, ScooterLocked> =
        userRepository.find(UserId(input.userId))
            .checkIsActive()
            .map { scooterRepository.find(ScooterId(input.scooterId)) }
            .flatMap { it.locked(UserId(input.userId)) }
            .onRight { scooterRepository.update(it) }
            .map { ScooterLocked(it.id.value) }
}

package com.example.application.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.example.application.domain.LockScooterError
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterLocked
import com.example.application.domain.UserId
import com.example.application.domain.service.GetActiveUser
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterInput
import com.example.application.port.output.FindScooterByScooterId
import com.example.application.port.output.UpdateScooter

class LockScooterUseCase(
    private val getActiveUser: GetActiveUser,
    private val findScooterByScooterId: FindScooterByScooterId,
    private val updateScooter: UpdateScooter,
) : LockScooter {
    override fun invoke(input: LockScooterInput): Either<LockScooterError, ScooterLocked> =
        getActiveUser(UserId(input.userId))
            .map { findScooterByScooterId(ScooterId(input.scooterId)) }
            .flatMap { it.locked(UserId(input.userId)) }
            .onRight { updateScooter(it) }
            .map { ScooterLocked(it.id.value) }
}

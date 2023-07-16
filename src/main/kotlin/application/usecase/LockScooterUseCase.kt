package com.example.application.usecase

import com.example.application.domain.ScooterId
import com.example.application.domain.UserId
import com.example.application.port.driven.ScooterRepository
import com.example.application.port.driven.UserRepository
import com.example.application.port.driver.LockScooter
import com.example.application.port.driver.LockScooterRequest
import com.example.application.port.driver.LockScooterResponse
import com.example.application.port.driver.ScooterLocked
import com.example.application.port.driver.ScooterNotFound
import com.example.application.port.driver.UserNotFound
import com.example.application.port.driver.UserStatusInvalid

class LockScooterUseCase(
    private val userRepository: UserRepository,
    private val scooterRepository: ScooterRepository,
) : LockScooter {

    override fun invoke(request: LockScooterRequest): LockScooterResponse {
        val user = userRepository.find(UserId(request.userId)) ?: return UserNotFound(request.userId)
        if (!user.isActive()) return UserStatusInvalid
        val scooter = scooterRepository.find(ScooterId(request.scooterId)) ?: return ScooterNotFound(request.scooterId)
        scooter.locked(user.id)
            .map { scooterRepository.update(it) }
        return ScooterLocked(scooter.id.value)
    }
}
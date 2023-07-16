package com.example.application.usecase

import com.example.application.domain.ScooterId
import com.example.application.domain.UserId
import com.example.application.port.driven.ScooterRepository
import com.example.application.port.driven.UserRepository
import com.example.application.port.driver.RunScooter
import com.example.application.port.driver.RunScooterRequest
import com.example.application.port.driver.RunScooterResponse
import com.example.application.port.driver.ScooterNotFound
import com.example.application.port.driver.ScooterRunning
import com.example.application.port.driver.UserNotFound
import com.example.application.port.driver.UserStatusInvalid

class RunScooterUseCase(
    private val userRepository: UserRepository,
    private val scooterRepository: ScooterRepository,
) : RunScooter {

    override fun invoke(request: RunScooterRequest): RunScooterResponse {
        val user = userRepository.find(UserId(request.userId)) ?: return UserNotFound(request.userId)
        if (!user.isActive()) return UserStatusInvalid
        val scooter = scooterRepository.find(ScooterId(request.scooterId)) ?: return ScooterNotFound(request.scooterId)
        scooter.running(user.id)
            .map { scooterRepository.update(it) }
        return ScooterRunning(scooter.id.value)
    }
}
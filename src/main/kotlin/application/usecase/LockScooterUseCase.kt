package com.example.application.usecase

import com.example.application.domain.ScooterId
import com.example.application.domain.UserId
import com.example.application.port.driven.ScooterFinder
import com.example.application.port.driven.UserFinder
import com.example.application.port.driver.LockScooter
import com.example.application.port.driver.LockScooterRequest
import com.example.application.port.driver.LockScooterResponse
import com.example.application.port.driver.ScooterLocked
import com.example.application.port.driver.ScooterNotFound
import com.example.application.port.driver.UserNotFound
import com.example.application.port.driver.UserStatusInvalid

class LockScooterUseCase(
    private val userFinder: UserFinder,
    private val scooterFinder: ScooterFinder,
) : LockScooter {

    override fun invoke(request: LockScooterRequest): LockScooterResponse {
        val user = userFinder.find(UserId(request.userId)) ?: return UserNotFound(request.userId)
        if (!user.isActive()) return UserStatusInvalid
        val scooter = scooterFinder.find(ScooterId(request.scooterId)) ?: return ScooterNotFound(request.scooterId)
        return scooter.locked().let { ScooterLocked(it.id.value) }
    }
}
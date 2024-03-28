package com.example.application.domain.service

import arrow.core.Either
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserInvalidStatus
import com.example.application.port.output.UserRepository

class GetActiveUser(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: UserId): Either<UserInvalidStatus, User> =
        userRepository.findBy(userId)
            .checkIsActive()
}
package com.example.application.port.driven

import arrow.core.Either
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserNotFound

interface UserRepository {

    fun findAll(): List<User>

    fun find(userId: UserId): Either<UserNotFound, User>
}
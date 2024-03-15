package com.example.application.port.output

import com.example.application.domain.User
import com.example.application.domain.UserId

interface UserRepository {
    fun findAll(): List<User>

    fun find(userId: UserId): User
}

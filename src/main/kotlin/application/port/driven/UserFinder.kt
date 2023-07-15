package com.example.application.port.driven

import com.example.application.domain.User
import com.example.application.domain.UserId

interface UserFinder {

    fun findAll(): List<User>

    fun find(userId: UserId): User?
}
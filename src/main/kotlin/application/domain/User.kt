package com.example.application.domain

import com.example.application.domain.UserStatus.ACTIVE

@JvmInline
value class UserId(val value: String)

@JvmInline
value class Name(val value: String)

enum class UserStatus {
    ACTIVE,
    DEACTIVATED
}

data class User(
    val id: UserId,
    val name: Name,
    val status: UserStatus,
) {

    fun isActive(): Boolean = status == ACTIVE
}
package com.example.application.port.input

import com.example.application.domain.UserStatus
import kotlinx.serialization.Serializable

interface GetUsers {
    operator fun invoke(): GetUsersResponse
}

@Serializable
data class GetUsersResponse(
    val scooters: List<UserResponse>
)

@Serializable
data class UserResponse(
    val id: String,
    val name: String,
    val status: UserStatus,
)
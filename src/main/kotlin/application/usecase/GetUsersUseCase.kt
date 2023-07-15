package com.example.application.usecase

import com.example.application.port.driven.UserRepository
import com.example.application.port.driver.GetUsers
import com.example.application.port.driver.GetUsersResponse
import com.example.application.port.driver.UserResponse

class GetUsersUseCase(
    private val userRepository: UserRepository,
) : GetUsers {

    override fun invoke(): GetUsersResponse =
        userRepository.findAll()
            .map { UserResponse(it.id.value, it.name.value, it.status) }
            .let { GetUsersResponse(it) }
}
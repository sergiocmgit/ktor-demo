package com.example.application.usecase

import com.example.application.port.input.GetUsers
import com.example.application.port.input.GetUsersResponse
import com.example.application.port.input.UserResponse
import com.example.application.port.output.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository,
) : GetUsers {

    override fun invoke(): GetUsersResponse =
        userRepository.findAll()
            .map { UserResponse(it.id.value, it.name.value, it.status) }
            .let { GetUsersResponse(it) }
}

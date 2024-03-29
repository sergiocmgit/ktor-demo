package com.example.application.usecase

import com.example.application.domain.User
import com.example.application.port.input.GetUsers
import com.example.application.port.output.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository,
) : GetUsers {
    override fun invoke(): List<User> = userRepository.findAll()
}

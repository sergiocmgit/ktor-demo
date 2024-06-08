package com.example.application.usecase

import com.example.application.domain.User
import com.example.application.port.input.GetUsers
import com.example.application.port.output.FindAllUsers

class GetUsersUseCase(
    private val findAllUsers: FindAllUsers,
) : GetUsers {
    override fun invoke(): List<User> = findAllUsers()
}

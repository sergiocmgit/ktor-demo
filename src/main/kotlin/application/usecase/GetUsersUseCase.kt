package com.example.application.usecase

import com.example.application.port.driven.UserFinder
import com.example.application.port.driver.GetUsers
import com.example.application.port.driver.GetUsersResponse
import com.example.application.port.driver.UserResponse

class GetUsersUseCase(
    private val userFinder: UserFinder,
) : GetUsers {

    override fun invoke(): GetUsersResponse =
        userFinder.findAll()
            .map { UserResponse(it.id.value, it.name.value, it.status) }
            .let { GetUsersResponse(it) }
}
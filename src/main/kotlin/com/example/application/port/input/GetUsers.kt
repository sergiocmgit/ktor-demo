package com.example.application.port.input

import com.example.application.domain.User

interface GetUsers {
    operator fun invoke(): List<User>
}

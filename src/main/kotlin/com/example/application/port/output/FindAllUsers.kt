package com.example.application.port.output

import com.example.application.domain.User

interface FindAllUsers {
    operator fun invoke(): List<User>
}

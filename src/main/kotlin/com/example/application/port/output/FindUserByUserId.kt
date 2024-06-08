package com.example.application.port.output

import com.example.application.domain.User
import com.example.application.domain.UserId

interface FindUserByUserId {
    operator fun invoke(userId: UserId): User
}

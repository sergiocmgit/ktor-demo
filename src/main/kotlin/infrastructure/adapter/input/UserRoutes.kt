package com.example.infrastructure.adapter.input

import com.example.application.domain.User
import com.example.application.domain.UserStatus
import com.example.application.port.input.GetUsers
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

fun Route.users(getUsers: GetUsers) =
    route("/users") {
        get {
            GetUsersResponse(getUsers().map(::UserDto))
                .let { call.respond(it) }
        }
    }

@Serializable
data class GetUsersResponse(
    val users: List<UserDto>,
)

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val status: UserStatus,
) {
    constructor(user: User) : this(
        id = user.id.value,
        name = user.name.value,
        status = user.status,
    )
}
package com.example.infrastructure.adapter.input

import com.example.application.port.input.GetUsers
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.users(getUsers: GetUsers) = route("/users") {

    get {
        call.respond(getUsers())
    }
}
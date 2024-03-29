package com.example.infrastructure.adapter.input

import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserNotFound
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.statusPages() {
    install(StatusPages) {
        exception<Exception> { call, exception ->
            when (exception) {
                is UserNotFound,
                is ScooterNotFound -> call.respond(NotFound, exception.javaClass.simpleName)
            }
        }
    }
}
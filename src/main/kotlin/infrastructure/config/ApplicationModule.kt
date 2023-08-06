package com.example.infrastructure.config

import com.example.infrastructure.adapter.output.DatabaseFactory
import io.ktor.server.application.Application

fun Application.rootModule() {
    routesModule()
    scooterModule()
    DatabaseFactory.init()
    // users(GetUsersUseCase(inMemoryUsers))
}

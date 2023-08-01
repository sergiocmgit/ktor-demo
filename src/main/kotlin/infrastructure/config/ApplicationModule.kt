package com.example.infrastructure.config

import io.ktor.server.application.Application

fun Application.rootModule() {
    routesModule()
    scooterModule()
    //users(GetUsersUseCase(inMemoryUsers))
}
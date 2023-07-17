package com.example.infrastructure.config

import com.example.application.usecase.GetScootersUseCase
import com.example.application.usecase.GetUsersUseCase
import com.example.application.usecase.LockScooterUseCase
import com.example.application.usecase.RunScooterUseCase
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.adapter.input.users
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.Routing
import kotlinx.serialization.json.Json

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Resources)
    install(Routing) {
        val inMemoryScooters = InMemoryScooters()
        val inMemoryUsers = InMemoryUsers()
        scooters(
            GetScootersUseCase(inMemoryScooters),
            RunScooterUseCase(inMemoryUsers, inMemoryScooters),
            LockScooterUseCase(inMemoryUsers, inMemoryScooters)
        )
        users(GetUsersUseCase(inMemoryUsers))
    }
}
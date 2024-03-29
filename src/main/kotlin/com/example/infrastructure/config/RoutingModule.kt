package com.example.infrastructure.config

import com.example.application.domain.service.GetActiveUser
import com.example.application.usecase.GetScootersUseCase
import com.example.application.usecase.GetUsersUseCase
import com.example.application.usecase.LockScooterUseCase
import com.example.application.usecase.RunScooterUseCase
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.adapter.input.statusPages
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

fun Application.routingModule(
    inMemoryScooters: InMemoryScooters,
    inMemoryUsers: InMemoryUsers,
) {
    contentNegotiation()
    install(Resources)
    statusPages()
    install(Routing) {
        scootersRouting(inMemoryScooters, inMemoryUsers)
        usersRouting(inMemoryUsers)
    }
}

private fun Routing.scootersRouting(
    inMemoryScooters: InMemoryScooters,
    inMemoryUsers: InMemoryUsers,
) {
    val getActiveUser = GetActiveUser(inMemoryUsers)
    scooters(
        getScooters = GetScootersUseCase(inMemoryScooters),
        runScooter = RunScooterUseCase(getActiveUser, inMemoryScooters),
        lockScooter = LockScooterUseCase(getActiveUser, inMemoryScooters),
    )
}

private fun Routing.usersRouting(inMemoryUsers: InMemoryUsers) {
    users(
        getUsers = GetUsersUseCase(inMemoryUsers),
    )
}

private fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            },
        )
    }
}

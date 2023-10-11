package com.example.infrastructure.config

import com.example.application.usecase.GetScootersUseCase
import com.example.application.usecase.GetUsersUseCase
import com.example.application.usecase.LockScooterUseCase
import com.example.application.usecase.RunScooterUseCase
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.adapter.input.users
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing

fun Application.routingModule(
    inMemoryScooters: InMemoryScooters,
    inMemoryUsers: InMemoryUsers,
) {
    install(Routing) {
        scootersRouting(inMemoryScooters, inMemoryUsers)
        usersRouting(inMemoryUsers)
    }
}

private fun Routing.scootersRouting(
    inMemoryScooters: InMemoryScooters,
    inMemoryUsers: InMemoryUsers,
) {
    scooters(
        getScooters = GetScootersUseCase(inMemoryScooters),
        runScooter = RunScooterUseCase(inMemoryUsers, inMemoryScooters),
        lockScooter = LockScooterUseCase(inMemoryUsers, inMemoryScooters),
    )
}

private fun Routing.usersRouting(inMemoryUsers: InMemoryUsers) {
    users(
        getUsers = GetUsersUseCase(inMemoryUsers),
    )
}

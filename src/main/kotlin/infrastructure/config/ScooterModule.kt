package com.example.infrastructure.config

import com.example.application.usecase.GetScootersUseCase
import com.example.application.usecase.LockScooterUseCase
import com.example.application.usecase.RunScooterUseCase
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing

fun Application.scooterModule() {
    install(Routing) {
        val inMemoryScooters = InMemoryScooters()
        val inMemoryUsers = InMemoryUsers()
        scooters(
            GetScootersUseCase(inMemoryScooters),
            RunScooterUseCase(inMemoryUsers, inMemoryScooters),
            LockScooterUseCase(inMemoryUsers, inMemoryScooters),
        )
    }
}

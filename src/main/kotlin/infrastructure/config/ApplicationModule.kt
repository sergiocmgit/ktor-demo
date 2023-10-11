package com.example.infrastructure.config

import com.example.infrastructure.adapter.output.DatabaseFactory
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import kotlinx.serialization.json.Json

fun Application.rootModule() {
    contentNegotiation()
    install(Resources)

    val inMemoryScooters = InMemoryScooters()
    val inMemoryUsers = InMemoryUsers()

    routingModule(inMemoryScooters, inMemoryUsers)
    DatabaseFactory.init()
}

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            },
        )
    }
}

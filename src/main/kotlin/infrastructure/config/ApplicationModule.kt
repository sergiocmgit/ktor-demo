package com.example.infrastructure.config

import com.example.application.usecase.GetScootersUseCase
import com.example.infrastructure.driven.InMemoryScooters
import com.example.infrastructure.driver.scooters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.Routing
import kotlinx.serialization.json.Json

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Routing) {
        scooters(GetScootersUseCase(InMemoryScooters()))
    }
}
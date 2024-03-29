package com.example.infrastructure.config

import com.example.infrastructure.adapter.input.statusPages
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import io.ktor.server.application.Application

fun Application.rootModule() {
    val inMemoryScooters = InMemoryScooters()
    val inMemoryUsers = InMemoryUsers()
    DatabaseFactory.init()

    routingModule(inMemoryScooters, inMemoryUsers)
    statusPages()
}

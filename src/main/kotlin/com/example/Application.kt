package com.example

import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.adapter.output.InMemoryUsers
import com.example.infrastructure.config.DatabaseInstance
import com.example.infrastructure.config.routingModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        val inMemoryScooters = InMemoryScooters()
        val inMemoryUsers = InMemoryUsers()
        DatabaseInstance.init()

        routingModule(inMemoryScooters, inMemoryUsers)
    }.start(wait = true)
}

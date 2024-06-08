package com.example

import com.example.infrastructure.adapter.output.H2FindAllUsers
import com.example.infrastructure.adapter.output.H2FindUserByUserId
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.config.DatabaseInstance
import com.example.infrastructure.config.routingModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        val inMemoryScooters = InMemoryScooters()
        val findAllUsers = H2FindAllUsers()
        val findUserByUserId = H2FindUserByUserId()
        DatabaseInstance.init()

        routingModule(inMemoryScooters, findAllUsers, findUserByUserId)
    }.start(wait = true)
}

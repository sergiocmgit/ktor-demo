package com.example

import com.example.infrastructure.adapter.output.H2FindAllScooters
import com.example.infrastructure.adapter.output.H2FindAllUsers
import com.example.infrastructure.adapter.output.H2FindScooterByScooterId
import com.example.infrastructure.adapter.output.H2FindUserByUserId
import com.example.infrastructure.adapter.output.InMemoryScooters
import com.example.infrastructure.config.DatabaseInstance
import com.example.infrastructure.config.routingModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        val inMemoryScooters = InMemoryScooters()
        val findAllScooters = H2FindAllScooters()
        val findScooterByScooterId = H2FindScooterByScooterId()
        val findAllUsers = H2FindAllUsers()
        val findUserByUserId = H2FindUserByUserId()
        DatabaseInstance.init()

        routingModule(inMemoryScooters, findAllScooters, findScooterByScooterId, findAllUsers, findUserByUserId)
    }.start(wait = true)
}

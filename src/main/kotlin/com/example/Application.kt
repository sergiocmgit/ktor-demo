package com.example

import com.example.infrastructure.adapter.output.H2FindAllScooters
import com.example.infrastructure.adapter.output.H2FindAllUsers
import com.example.infrastructure.adapter.output.H2FindScooterByScooterId
import com.example.infrastructure.adapter.output.H2FindUserByUserId
import com.example.infrastructure.adapter.output.H2UpdateScooter
import com.example.infrastructure.config.DatabaseInstance
import com.example.infrastructure.config.routingModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        val findAllScooters = H2FindAllScooters()
        val findScooterByScooterId = H2FindScooterByScooterId()
        val updateScooter = H2UpdateScooter()
        val findAllUsers = H2FindAllUsers()
        val findUserByUserId = H2FindUserByUserId()
        DatabaseInstance.init()

        routingModule(findAllScooters, findScooterByScooterId, updateScooter, findAllUsers, findUserByUserId)
    }.start(wait = true)
}

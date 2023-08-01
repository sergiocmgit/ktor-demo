package com.example

import com.example.infrastructure.config.rootModule
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "localhost",
        module = Application::rootModule
    ).start(wait = true)
}

package com.example

import com.example.infrastructure.config.module
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "localhost",
        module = Application::module
    ).start(wait = true)
}

package com.example.infrastructure.driver

import com.example.application.port.driver.GetScooters
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.scooters(getScooters: GetScooters) = route("/scooters") {

    get {
        call.respond(getScooters())
    }
}
package com.example.infrastructure.driver

import com.example.application.port.driver.GetScooters
import com.example.application.port.driver.LockScooter
import com.example.application.port.driver.LockScooterRequest
import com.example.application.port.driver.RunScooter
import com.example.application.port.driver.RunScooterRequest
import com.example.application.port.driver.ScooterLocked
import com.example.application.port.driver.ScooterNotFound
import com.example.application.port.driver.ScooterRunning
import com.example.application.port.driver.UserNotFound
import com.example.application.port.driver.UserStatusInvalid
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.call
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.scooters(
    getScooters: GetScooters,
    runScooter: RunScooter,
    lockScooter: LockScooter,
) = route("/scooters") {

    get {
        call.respond(getScooters())
    }

    post<Id.Run> { params ->
        val request = RunScooterRequest(scooterId = params.parent.scooterId, userId = params.userId)
        val result = runScooter(request)
        when(result) {
            is ScooterRunning -> call.respond(OK)
            is ScooterNotFound -> call.respond(NotFound, result.javaClass.simpleName)
            is UserNotFound -> call.respond(NotFound, result.javaClass.simpleName)
            UserStatusInvalid -> call.respond(BadRequest, result.javaClass.simpleName)
        }
    }

    post<Id.Lock> { params ->
        val request = LockScooterRequest(scooterId = params.parent.scooterId, userId = params.userId)
        val result = lockScooter(request)
        when (result) {
            is ScooterLocked -> call.respond(OK)
            is ScooterNotFound -> call.respond(NotFound, result.javaClass.simpleName)
            is UserNotFound -> call.respond(NotFound, result.javaClass.simpleName)
            UserStatusInvalid -> call.respond(BadRequest, result.javaClass.simpleName)
        }
    }
}
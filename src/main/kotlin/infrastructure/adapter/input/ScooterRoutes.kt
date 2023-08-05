package com.example.infrastructure.adapter.input

import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserInvalidStatus
import com.example.application.domain.UserNotFound
import com.example.application.port.input.GetScooters
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterRequest
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterRequest
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.scooters(
    getScooters: GetScooters,
    runScooter: RunScooter,
    lockScooter: LockScooter,
) {
    get<Scooter> { call.respond(getScooters()) }

    post<Scooter.Id.Run> { params ->
        val request = RunScooterRequest(scooterId = params.parent.scooterId, userId = params.userId)
        runScooter(request).fold(ifRight = { call.respond(OK) }, ifLeft = {
            when (it) {
                ScooterInvalidStatus,
                UserInvalidStatus,
                -> call.respond(BadRequest, it.javaClass.simpleName)

                ScooterNotFound,
                UserNotFound,
                -> call.respond(NotFound, it.javaClass.simpleName)
            }
        })
    }

    post<Scooter.Id.Lock> { params ->
        val request = LockScooterRequest(scooterId = params.parent.scooterId, userId = params.userId)
        lockScooter(request).fold(ifRight = { call.respond(OK) }, ifLeft = {
            when (it) {
                ScooterInvalidStatus,
                UserInvalidStatus,
                -> call.respond(BadRequest, it.javaClass.simpleName)

                ScooterNotFound,
                UserNotFound,
                -> call.respond(NotFound, it.javaClass.simpleName)
            }
        })
    }
}

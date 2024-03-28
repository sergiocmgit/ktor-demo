package com.example.infrastructure.adapter.input

import com.example.application.domain.ScooterInvalidStatus
import com.example.application.domain.UserInvalidStatus
import com.example.application.port.input.GetScooters
import com.example.application.port.input.LockScooter
import com.example.application.port.input.LockScooterInput
import com.example.application.port.input.RunScooter
import com.example.application.port.input.RunScooterInput
import io.ktor.http.HttpStatusCode.Companion.BadRequest
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
        val input = RunScooterInput(scooterId = params.parent.scooterId, userId = params.userId)
        runScooter(input).fold(ifRight = { call.respond(OK) }, ifLeft = {
            when (it) {
                ScooterInvalidStatus,
                UserInvalidStatus,
                -> call.respond(BadRequest, it.javaClass.simpleName)
            }
        })
    }

    post<Scooter.Id.Lock> { params ->
        val input = LockScooterInput(scooterId = params.parent.scooterId, userId = params.userId)
        lockScooter(input).fold(ifRight = { call.respond(OK) }, ifLeft = {
            when (it) {
                ScooterInvalidStatus,
                UserInvalidStatus,
                -> call.respond(BadRequest, it.javaClass.simpleName)
            }
        })
    }
}

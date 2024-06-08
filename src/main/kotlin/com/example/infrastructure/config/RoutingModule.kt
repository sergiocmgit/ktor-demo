package com.example.infrastructure.config

import com.example.application.domain.service.GetActiveUser
import com.example.application.port.output.FindAllScooters
import com.example.application.port.output.FindAllUsers
import com.example.application.port.output.FindScooterByScooterId
import com.example.application.port.output.FindUserByUserId
import com.example.application.port.output.UpdateScooter
import com.example.application.usecase.GetScootersUseCase
import com.example.application.usecase.GetUsersUseCase
import com.example.application.usecase.LockScooterUseCase
import com.example.application.usecase.RunScooterUseCase
import com.example.infrastructure.adapter.input.scooters
import com.example.infrastructure.adapter.input.statusPages
import com.example.infrastructure.adapter.input.users
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.Routing
import kotlinx.serialization.json.Json

fun Application.routingModule(
    findAllScooters: FindAllScooters,
    findScooterByScooterId: FindScooterByScooterId,
    updateScooter: UpdateScooter,
    findAllUsers: FindAllUsers,
    findUserByUserId: FindUserByUserId,
) {
    contentNegotiation()
    install(Resources)
    statusPages()
    install(Routing) {
        scootersRouting(findAllScooters, findScooterByScooterId, updateScooter, findUserByUserId)
        usersRouting(findAllUsers)
    }
}

private fun Routing.scootersRouting(
    findAllScooters: FindAllScooters,
    findScooterByScooterId: FindScooterByScooterId,
    updateScooter: UpdateScooter,
    findUserByUserId: FindUserByUserId,
) {
    val getActiveUser = GetActiveUser(findUserByUserId)
    scooters(
        getScooters = GetScootersUseCase(findAllScooters),
        runScooter = RunScooterUseCase(getActiveUser, findScooterByScooterId, updateScooter),
        lockScooter = LockScooterUseCase(getActiveUser, findScooterByScooterId, updateScooter),
    )
}

private fun Routing.usersRouting(findAllUsers: FindAllUsers) {
    users(
        getUsers = GetUsersUseCase(findAllUsers),
    )
}

private fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            },
        )
    }
}

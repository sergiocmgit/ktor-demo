package com.example.infrastructure.adapter.output

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.ScooterStatus
import com.example.application.domain.ScooterStatus.LOCKED
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.port.output.ScooterRepository

class InMemoryScooters(scooters: MutableList<Scooter>? = null) : ScooterRepository {

    private val scooters: MutableList<Scooter> = scooters ?: mutableListOf(
        buildScooter(SCOOTER_ID_1, LOCKED, USER_ID_1),
        buildScooter(SCOOTER_ID_2, RUNNING, USER_ID_2),
        buildScooter(SCOOTER_ID_3, LOCKED, USER_ID_3)
    )

    override fun find(scooterId: ScooterId): Either<ScooterNotFound, Scooter> =
        scooters.find { it.id == scooterId }?.right()
            ?: ScooterNotFound.left()

    override fun findAll(): List<Scooter> = scooters

    override fun update(scooter: Scooter) {
        scooters.indexOfFirst { it.id == scooter.id }
            .also { scooters.removeAt(it) }
            .also { scooters.add(it, scooter) }
    }

    private fun buildScooter(id: Int, status: ScooterStatus, userId: String) =
        Scooter(
            ScooterId(id),
            status,
            UserId(userId)
        )

}

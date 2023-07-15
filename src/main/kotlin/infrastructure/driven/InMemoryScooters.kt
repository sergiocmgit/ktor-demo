package com.example.infrastructure.driven

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterStatus
import com.example.application.domain.ScooterStatus.RUNNING
import com.example.application.domain.UserId
import com.example.application.port.driven.ScooterRepository

class InMemoryScooters : ScooterRepository {

    private val scooters: MutableList<Scooter> = mutableListOf(
        buildScooter(SCOOTER_ID_1, RUNNING, USER_ID_1),
        buildScooter(SCOOTER_ID_2, RUNNING, USER_ID_2),
        buildScooter(SCOOTER_ID_3, RUNNING, USER_ID_3)
    )

    override fun find(scooterId: ScooterId): Scooter? = scooters.find { it.id == scooterId }

    override fun findAll(): List<Scooter> = scooters

    override fun update(scooter: Scooter) {
        scooters.first { it.id == scooter.id }
            .let { scooters.remove(it) }
            .let { scooters.add(scooter) }
    }

    private fun buildScooter(id: Int, status: ScooterStatus, userId: String) =
        Scooter(
            ScooterId(id),
            status,
            UserId(userId)
        )

}

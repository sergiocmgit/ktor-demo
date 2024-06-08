package com.example.infrastructure.adapter.output

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserId
import com.example.application.port.output.ScooterRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class InMemoryScooters : ScooterRepository {
    override fun findBy(scooterId: ScooterId): Scooter =
        transaction {
            ScooterTable.select { ScooterTable.id eq scooterId.value }
                .limit(1)
                .singleOrNull()
                ?.toDomain()
                ?: throw ScooterNotFound
        }

    override fun update(scooter: Scooter) {
        transaction {
            ScooterTable.update({ ScooterTable.id eq scooter.id.value }) {
                it[status] = scooter.status
                it[lastRider] = scooter.lastRider.value
            }
        }
    }

    private fun ResultRow.toDomain() =
        Scooter(
            id = ScooterId(this[ScooterTable.id]),
            status = this[ScooterTable.status],
            lastRider = UserId(this[ScooterTable.lastRider]),
        )
}

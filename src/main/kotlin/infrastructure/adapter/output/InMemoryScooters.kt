package com.example.infrastructure.adapter.output

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserId
import com.example.application.port.output.ScooterRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class InMemoryScooters : ScooterRepository {
    override fun find(scooterId: ScooterId): Either<ScooterNotFound, Scooter> =
        transaction {
            ScooterTable.select { ScooterTable.id eq scooterId.value }
                .map { it.toDomain() }
                .singleOrNull()?.right()
                ?: ScooterNotFound.left()
        }

    override fun findAll(): List<Scooter> =
        transaction {
            ScooterTable.selectAll().map { it.toDomain() }
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

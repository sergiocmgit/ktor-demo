package com.example.infrastructure.adapter.output

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.UserId
import com.example.application.port.output.FindAllScooters
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class H2FindAllScooters : FindAllScooters {
    override fun invoke(): List<Scooter> =
        transaction {
            ScooterTable.selectAll().map { it.toDomain() }
        }

    private fun ResultRow.toDomain() =
        Scooter(
            id = ScooterId(this[ScooterTable.id]),
            status = this[ScooterTable.status],
            lastRider = UserId(this[ScooterTable.lastRider]),
        )
}

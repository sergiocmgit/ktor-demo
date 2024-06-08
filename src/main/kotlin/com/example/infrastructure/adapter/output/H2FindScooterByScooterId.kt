package com.example.infrastructure.adapter.output

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.UserId
import com.example.application.port.output.FindScooterByScooterId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class H2FindScooterByScooterId : FindScooterByScooterId {
    override fun invoke(scooterId: ScooterId): Scooter =
        transaction {
            ScooterTable.select { ScooterTable.id eq scooterId.value }
                .limit(1)
                .singleOrNull()
                ?.toDomain()
                ?: throw ScooterNotFound
        }

    private fun ResultRow.toDomain() =
        Scooter(
            id = ScooterId(this[ScooterTable.id]),
            status = this[ScooterTable.status],
            lastRider = UserId(this[ScooterTable.lastRider]),
        )
}

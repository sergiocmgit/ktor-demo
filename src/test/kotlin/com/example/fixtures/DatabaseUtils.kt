package com.example.fixtures

import com.example.application.domain.Scooter
import com.example.application.domain.ScooterId
import com.example.application.domain.ScooterNotFound
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.infrastructure.adapter.output.ScooterTable
import com.example.infrastructure.adapter.output.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseUtils {
    companion object {
        fun save(scooter: Scooter) {
            transaction {
                ScooterTable.insert {
                    it[id] = scooter.id.value
                    it[status] = scooter.status
                    it[lastRider] = scooter.lastRider.value
                }
            }
        }

        fun save(user: User) {
            transaction {
                UserTable.insert {
                    it[id] = user.id.value
                    it[name] = user.name.value
                    it[status] = user.status
                }
            }
        }

        fun findScooterBy(scooterId: ScooterId): Scooter =
            transaction {
                ScooterTable.select { ScooterTable.id eq scooterId.value }
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
}

package com.example.infrastructure.adapter.output

import com.example.application.domain.Scooter
import com.example.application.port.output.UpdateScooter
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class H2UpdateScooter : UpdateScooter {
    override fun invoke(scooter: Scooter) {
        transaction {
            ScooterTable.update({ ScooterTable.id eq scooter.id.value }) {
                it[status] = scooter.status
                it[lastRider] = scooter.lastRider.value
            }
        }
    }
}

package fixtures

import com.example.application.domain.Scooter
import com.example.infrastructure.adapter.output.ScooterTable
import org.jetbrains.exposed.sql.insert
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
    }
}

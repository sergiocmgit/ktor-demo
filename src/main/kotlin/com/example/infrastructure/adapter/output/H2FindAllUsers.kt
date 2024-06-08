package com.example.infrastructure.adapter.output

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.port.output.FindAllUsers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class H2FindAllUsers : FindAllUsers {
    override fun invoke(): List<User> =
        transaction {
            UserTable.selectAll().map { it.toDomain() }
        }

    private fun ResultRow.toDomain() =
        User(
            id = UserId(this[UserTable.id]),
            name = Name(this[UserTable.name]),
            status = this[UserTable.status],
        )
}

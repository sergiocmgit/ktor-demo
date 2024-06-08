package com.example.infrastructure.adapter.output

import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserNotFound
import com.example.application.port.output.FindUserByUserId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class H2FindUserByUserId : FindUserByUserId {
    override fun invoke(userId: UserId): User =
        transaction {
            UserTable.select { UserTable.id eq userId.value }
                .singleOrNull()
                ?.toDomain()
                ?: throw UserNotFound
        }

    private fun ResultRow.toDomain() =
        User(
            id = UserId(this[UserTable.id]),
            name = Name(this[UserTable.name]),
            status = this[UserTable.status],
        )
}

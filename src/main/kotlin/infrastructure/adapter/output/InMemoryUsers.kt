package com.example.infrastructure.adapter.output

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.application.domain.Name
import com.example.application.domain.User
import com.example.application.domain.UserId
import com.example.application.domain.UserNotFound
import com.example.application.port.output.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class InMemoryUsers : UserRepository {
    override fun findAll(): List<User> =
        transaction {
            UserTable.selectAll().map { it.toDomain() }
        }

    override fun find(userId: UserId): Either<UserNotFound, User> =
        transaction {
            UserTable.select { UserTable.id eq userId.value }
                .map { it.toDomain() }
                .singleOrNull()?.right()
                ?: UserNotFound.left()
        }

    private fun ResultRow.toDomain() =
        User(
            id = UserId(this[UserTable.id]),
            name = Name(this[UserTable.name]),
            status = this[UserTable.status],
        )
}

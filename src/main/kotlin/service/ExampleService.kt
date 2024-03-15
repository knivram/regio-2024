package service

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object ExampleService {
    val database = Database.connect(
        "jdbc:mysql://localhost:3306/mysql",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
    )

    init {
        transaction {
            addLogger(StdOutSqlLogger)
            // insert all table with `SchemaUtils.create(Users, etc.)`
        }
    }
}
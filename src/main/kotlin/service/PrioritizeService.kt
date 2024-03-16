package service

import repository.Repository
import java.util.*

object PrioritizeService {
    object AnalyzeRepository : Repository<Analyze>() {
        fun new(name: String) {
            if (this.getAll().find { it.name == name } == null) {
                val newAnalyze = Analyze(
                    UUID.randomUUID(),
                    name
                )
                this.new(newAnalyze)
            } else {
                throw RuntimeException("Analyze already exists")
            }
        }
    }
}
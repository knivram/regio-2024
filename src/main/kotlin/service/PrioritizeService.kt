package service

import repository.Repository
import java.util.*

object PrioritizeService {
    object AnalyzeRepository : Repository<Analyze>() {
        fun new(name: String) {
            if (this.getAll().find { it.name == name } == null) {
                val newAnalyze = Analyze(
                    id = UUID.randomUUID(),
                    name = name,
                    variantRepo = object : Repository<Variant>(){},
                    criterionRepo = object : Repository<Criterion>() {
                        fun neu(criterionName: String, criterionWeight: Int) {
                            if (this.getAll().find { it.name == criterionName } == null) {
                                this.new(Criterion(
                                    id = UUID.randomUUID(),
                                    name = criterionName,
                                    weight = criterionWeight
                                ))
                            } else {
                                throw RuntimeException("Criterion already exists")
                            }
                        }
                    },
                )
                this.new(newAnalyze)
            } else {
                throw RuntimeException("Analyze already exists")
            }
        }
    }
}
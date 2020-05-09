package com.teamplay.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.teamplay"])
@EntityScan(basePackages = ["com.teamplay"])
@EnableJpaRepositories(basePackages = ["com.teamplay"])
class TeamPlayApplication

    fun main(args: Array<String>) {
        runApplication<TeamPlayApplication>(*args)
    }



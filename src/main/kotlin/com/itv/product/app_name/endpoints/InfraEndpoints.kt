package com.itv.product.app_name.endpoints

import com.itv.product.app_name.config.Properties
import com.itv.product.app_name.infra.doHealthCheck
import com.itv.product.app_name.infra.model.VersionResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Routing.initialiseInfraEndpoints() = apply {

    get("/ping") {
        logger.info("pong")
        call.respond("pong")
    }
    get("/status") {
        logger.info("status")
        doHealthCheck().apply {
            if (isHealthy) {
                call.respond(HttpStatusCode.OK, this)
            } else {
                call.respond(HttpStatusCode.InternalServerError, this)
            }
        }
    }
    get("/version") {
        logger.info("version")
        call.respond(
            VersionResponse(
                Properties.commitId,
                Properties.commitMessage,
                Properties.commitTime
            )
        )
    }
}

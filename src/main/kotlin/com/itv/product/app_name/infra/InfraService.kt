package com.itv.product.app_name.infra

import com.itv.product.app_name.infra.model.HealthCheck
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun doHealthCheck(): HealthCheck {
    logger.info("Performing health check")
    // TODO implement service specific health check
    return HealthCheck(isHealthy = true)
}

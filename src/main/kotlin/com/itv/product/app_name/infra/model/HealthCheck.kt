package com.itv.product.app_name.infra.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HealthCheck(
    val isHealthy: Boolean
)

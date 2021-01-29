package com.itv.product.app_name.infra.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class VersionResponse(
    val commitId: String,
    val commitMessage: String,
    val commitTime: String
)

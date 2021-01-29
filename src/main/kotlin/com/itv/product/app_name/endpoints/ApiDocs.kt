package com.itv.product.app_name.endpoints

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.initialiseApiDocsEndpoints() = apply {
    get("/v3/api-docs") {
        this::class.java.classLoader.getResource("swagger.json")
            ?.let { call.respond(it.readText()) }
            ?: call.respond(HttpStatusCode.InternalServerError)
    }
}

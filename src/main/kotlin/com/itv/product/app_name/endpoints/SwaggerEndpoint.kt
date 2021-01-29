package com.itv.product.app_name.endpoints

import io.ktor.http.content.resources
import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.initialiseSwaggerEndpoint() {
    route("swagger") {
        resources("webjars/swagger")
    }
}

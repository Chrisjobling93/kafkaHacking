package com.itv.product.app_name

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.itv.product.app_name.config.Properties
import com.itv.product.app_name.config.initInfluxConfig
import com.itv.product.app_name.config.metricRegistry
import com.itv.product.app_name.endpoints.initialiseApiDocsEndpoints
import com.itv.product.app_name.endpoints.initialiseInfraEndpoints
import com.itv.product.app_name.endpoints.initialiseSwaggerEndpoint
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallId
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.callIdMdc
import io.ktor.jackson.jackson
import io.ktor.metrics.micrometer.MicrometerMetrics
import io.ktor.request.header
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import io.ktor.util.KtorExperimentalAPI
import io.ktor.webjars.Webjars
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@KtorExperimentalAPI
fun Application.module() {
    install(MicrometerMetrics) {
        initInfluxConfig()
        registry = metricRegistry
    }

    install(ContentNegotiation) {

        val javaTimeModule = JavaTimeModule().addSerializer(
            ZonedDateTime::class.java,
            ZonedDateTimeSerializer(DateTimeFormatter.ISO_INSTANT)
        )

        jackson {
            registerModule(Jdk8Module())
            registerModule(javaTimeModule)
            registerModule(ParameterNamesModule())
            registerModule(Jackson2HalModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }

    install(CallId) {
        retrieve { it.request.header(Properties.X_TRACE_ID) }
        generate { UUID.randomUUID().toString() }
        verify { it.isNotEmpty() }
    }

    install(CallLogging) {
        callIdMdc(Properties.X_TRACE_ID)
    }

    install(Webjars)
    install(Routing) {

        routing {
            initialiseInfraEndpoints()
            initialiseSwaggerEndpoint()
            initialiseApiDocsEndpoints()
        }
    }
}

@KtorExperimentalAPI
fun main() {
    embeddedServer(Jetty, Properties.server_port, module = Application::module).start(wait = true)
}

package com.itv.product.app_name.config

import io.micrometer.core.instrument.Clock
import io.micrometer.influx.InfluxConfig
import io.micrometer.influx.InfluxMeterRegistry
import java.time.Duration

lateinit var metricRegistry: InfluxMeterRegistry

fun initInfluxConfig() {
    metricRegistry = InfluxMeterRegistry(influxConfig, Clock.SYSTEM)
    metricRegistry.config().commonTags("env", Properties.env, "host", Properties.host)
}

var influxConfig: InfluxConfig = object : InfluxConfig {
    override fun step(): Duration {
        return Duration.ofSeconds(Properties.influxStep)
    }

    override fun db(): String {
        return Properties.influxDb
    }

    override fun uri(): String {
        return Properties.influxUrl
    }

    override fun get(k: String): String? {
        return null // accept the rest of the defaults
    }
}

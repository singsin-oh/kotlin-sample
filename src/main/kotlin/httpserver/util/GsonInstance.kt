package com.singsin.studio.httpserver.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.singsin.studio.httpserver.util.gson.LocalDateAdapter
import com.singsin.studio.httpserver.util.gson.LocalDateTimeAdapter
import com.singsin.studio.httpserver.util.gson.LocalTimeAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object GsonInstance {

    val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
            .serializeNulls()
            .create()
    }
}

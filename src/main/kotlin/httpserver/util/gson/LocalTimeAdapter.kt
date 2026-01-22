package com.singsin.studio.httpserver.util.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter(
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm:ss")
) : TypeAdapter<LocalTime>() {

    override fun write(out: JsonWriter, value: LocalTime?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.value(value.format(formatter))
    }

    override fun read(reader: JsonReader): LocalTime? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        return LocalTime.parse(reader.nextString(), formatter)
    }
}

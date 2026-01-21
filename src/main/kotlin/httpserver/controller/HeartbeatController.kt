package com.singsin.studio.httpserver.controller

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.nio.charset.StandardCharsets

object HeartbeatController : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        if (exchange.requestMethod != "GET") {
            writeResponse(exchange, 405, "Method Not Allowed")
            exchange.close()
            return
        }
        writeResponse(exchange, 200, """{"code":200,"message":"isOk","success":true}""")
        exchange.close()
    }

    private fun writeResponse(exchange: HttpExchange, status: Int, body: String) {
        val bytes = body.toByteArray(StandardCharsets.UTF_8)
        exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
        exchange.sendResponseHeaders(status, bytes.size.toLong())
        exchange.responseBody.use {
            it.write(bytes)
        }
    }
}

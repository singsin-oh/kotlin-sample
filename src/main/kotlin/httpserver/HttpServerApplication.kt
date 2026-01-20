package com.singsin.studio.httpserver

import com.sun.net.httpserver.HttpServer

fun main() {
    val start: HttpServer = start(5672)
    registerRoutes(
        start
    )
    println("Server running at http://localhost:5672")
}
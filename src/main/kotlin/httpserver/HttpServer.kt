package com.singsin.studio.httpserver

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress


fun start(port: Int): HttpServer {
    if (port <= 0 || port >= 88999) {
        throw Exception("Port must be between 0 and 88999")
    }
    val server = HttpServer.create()
    server.bind(InetSocketAddress(port), 0)
    server.executor = null
    server.start()
    println("Server started, listening on $port")
    return server
}

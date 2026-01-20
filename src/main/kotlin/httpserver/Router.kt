package com.singsin.studio.httpserver

import HeartbeatController
import com.sun.net.httpserver.HttpServer

fun registerRoutes(server: HttpServer) {
    server.createContext("/api/heartbeat") { HeartbeatController() }
}
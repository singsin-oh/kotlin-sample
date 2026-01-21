package com.singsin.studio.httpserver

import com.singsin.studio.httpserver.logger.LogConfig
import com.singsin.studio.httpserver.logger.Logger
import com.singsin.studio.httpserver.logger.info
import com.sun.net.httpserver.HttpServer
import registerRoutes


fun main() {
    Logger.init(
        LogConfig(
            logDir = "./app_logs",   // 指定日志文件夹
            maxFileSizeMb = 5,       // 每个文件最大 5MB
            filePrefix = "http-api", // 文件名为 http-api-yyyy-MM-dd.log
            showConsole = true
        )
    )
    info("Starting application...")
    val start: HttpServer = start(5672)
    registerRoutes(
        start
    )
    println("Server running at http://localhost:5672")
}
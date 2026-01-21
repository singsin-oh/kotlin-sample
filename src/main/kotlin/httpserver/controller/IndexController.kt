package com.singsin.studio.httpserver.controller

import BaseController
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object IndexController : BaseController(), HttpHandler {
    override fun handle(exchange: HttpExchange) {
        writeHtmlFile(exchange, 200, "/index.html")
    }
}
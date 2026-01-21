package com.singsin.studio.httpserver.midderware

import com.singsin.studio.httpserver.logger.info
import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.HttpExchange

class LoggerMiddleware : Filter() {

    private val keyHeaders = setOf(
        "Content-Type",
        "User-Agent",
        "Host",
        "Authorization",
        "Content-Length"
    )

    override fun doFilter(exchange: HttpExchange?, chain: Chain?) {
        if (exchange != null) {
            try {
                info("★★★========== Request ==========★★★")
                info("Method : ${exchange.requestMethod}")
                info("Path   : ${exchange.requestURI}")
                info("IP     : ${exchange.remoteAddress?.address?.hostAddress}")
                val headers = exchange.requestHeaders
                keyHeaders.forEach { key ->
                    val value = headers.getFirst(key)
                    if (value != null) {
                        val printValue = if (key.equals("Authorization", ignoreCase = true)) {
                            "Bearer ****** (Masked)"
                        } else {
                            value
                        }
                        info("Header : $key = $printValue")
                    }
                }
                info("★★★==========★★★★★★★★★★==========★★★")
            } catch (e: Exception) {
                info("Logger Error: ${e.message}")
            }
        }
        chain?.doFilter(exchange)
    }

    override fun description(): String {
        return "Key Info Line-by-Line Logger"
    }
}
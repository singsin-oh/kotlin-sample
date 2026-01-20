import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.nio.charset.StandardCharsets

class HeartbeatController : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val path = exchange.requestURI.path
        println("Received request: $path, method: ${exchange.requestMethod}")

        if (exchange.requestMethod != "GET") {
            writeResponse(exchange, 405, "Method Not Allowed")
            return
        }

        writeResponse(exchange, 200, """{"code":200,"message":"isOk","success":true}""")
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

import com.singsin.studio.httpserver.response.HTTPResponse
import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets

abstract class BaseController {

    protected fun writeJson(
        exchange: HttpExchange,
        status: Int,
        response: HTTPResponse
    ) {
        val bytes = response.toJson().toByteArray(StandardCharsets.UTF_8)

        exchange.responseHeaders.add("Content-Type", "application/json; charset=utf-8")
        exchange.sendResponseHeaders(status, bytes.size.toLong())

        exchange.responseBody.use {
            it.write(bytes)
        }
    }

    protected fun writeText(
        exchange: HttpExchange,
        status: Int,
        text: String
    ) {
        val bytes = text.toByteArray(StandardCharsets.UTF_8)

        exchange.responseHeaders.add("Content-Type", "text/plain; charset=utf-8")
        exchange.sendResponseHeaders(status, bytes.size.toLong())

        exchange.responseBody.use {
            it.write(bytes)
        }
    }

    protected fun writeError(
        exchange: HttpExchange,
        status: Int,
        message: String
    ) {
        writeJson(
            exchange,
            status,
            HTTPResponse(status, message, false)
        )
    }
}

import com.singsin.studio.httpserver.controller.HeartbeatController
import com.singsin.studio.httpserver.controller.IndexController
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

fun HttpServer.createContextWithLogger(path: String, handler: HttpHandler) {
    val context = this.createContext(path, handler)
    context.filters.add(com.singsin.studio.httpserver.midderware.LoggerMiddleware())
}

fun registerRoutes(server: HttpServer) {
    server.createContextWithLogger("/index", IndexController)
    server.createContextWithLogger("/api/heartbeat", HeartbeatController)
}
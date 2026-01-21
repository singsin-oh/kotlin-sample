package com.singsin.studio.httpserver.logger

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * 日志级别枚举
 */
enum class LogLevel(val colorCode: String) {
    DEBUG("\u001B[36m"), // 青色
    INFO("\u001B[32m"),  // 绿色
    WARN("\u001B[33m"),  // 黄色
    ERROR("\u001B[31m"); // 红色

    val reset = "\u001B[0m"
}

/**
 * 日志配置类
 */
data class LogConfig(
    val logDir: String = "logs",           // 日志存储目录
    val maxFileSizeMb: Int = 10,           // 单个文件最大大小 (MB)
    val filePrefix: String = "server",     // 日志文件名前缀
    val showConsole: Boolean = true,       // 是否输出到控制台
    val keepDays: Int = 30                 // (可选扩展) 保留多少天的日志
)

/**
 * 核心 Logger 单例对象
 */
object Logger {
    private var config: LogConfig = LogConfig()
    private val lock = ReentrantLock()
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    fun init(newConfig: LogConfig) {
        this.config = newConfig
        val dir = File(config.logDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    // 核心日志方法
    fun log(level: LogLevel, msg: String, throwable: Throwable? = null) {
        val tag = getCallerTag()

        val timestamp = LocalDateTime.now().format(timeFormatter)
        val threadName = Thread.currentThread().name

        val logMessage = buildString {
            append("[$timestamp] ")
            append("[${level.name}] ")
            append("[$threadName] ")
            append("[$tag] : ")
            append(msg)
            if (throwable != null) {
                append("\n")
                append(getStackTraceString(throwable))
            }
        }

        if (config.showConsole) {
            println("${level.colorCode}$logMessage${level.reset}")
        }
        lock.withLock {
            writeToFile(logMessage)
        }
    }

    private fun writeToFile(message: String) {
        try {
            val logFile = getRotatedFile()

            // 追加模式写入
            FileWriter(logFile, true).use { writer ->
                writer.write(message + System.lineSeparator())
            }
        } catch (e: Exception) {
            System.err.println("Logger Write Failed: ${e.message}")
        }
    }

    private fun getRotatedFile(): File {
        val today = LocalDate.now().format(dateFormatter)
        val baseFileName = "${config.filePrefix}-$today.log"
        var targetFile = File(config.logDir, baseFileName)

        // 检查文件大小是否超过限制 (按大小轮转)
        if (targetFile.exists() && isFileOverSize(targetFile)) {
            var index = 1
            var rotatedFile = File(config.logDir, "${config.filePrefix}-$today.$index.log")
            while (rotatedFile.exists() && isFileOverSize(rotatedFile)) {
                index++
                rotatedFile = File(config.logDir, "${config.filePrefix}-$today.$index.log")
            }
            targetFile = rotatedFile
        }

        return targetFile
    }

    private fun isFileOverSize(file: File): Boolean {
        val sizeInMb = file.length().toDouble() / (1024 * 1024)
        return sizeInMb >= config.maxFileSizeMb
    }

    private fun getCallerTag(): String {
        val stackTrace = Thread.currentThread().stackTrace
        for (element in stackTrace) {
            val className = element.className
            if (!className.startsWith("java.lang") &&
                !className.startsWith("kotlin.io") &&
                !className.contains("LoggerKt") && // 过滤掉顶层函数文件
                !className.contains("Logger") // 过滤掉 Logger 对象本身
            ) {
                return className.substringAfterLast('.')
            }
        }
        return "Unknown"
    }

    private fun getStackTraceString(t: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        t.printStackTrace(pw)
        return sw.toString()
    }
}

fun info(msg: String) {
    Logger.log(LogLevel.INFO, msg)
}

fun warn(msg: String) {
    Logger.log(LogLevel.WARN, msg)
}

fun error(msg: String, error: Throwable? = null) {
    Logger.log(LogLevel.ERROR, msg, error)
}

fun debug(msg: String) {
    Logger.log(LogLevel.DEBUG, msg)
}
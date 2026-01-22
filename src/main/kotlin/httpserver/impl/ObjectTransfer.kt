package com.singsin.studio.httpserver.impl

import com.singsin.studio.httpserver.biz.IObjectTransfer
import com.singsin.studio.httpserver.domain.transfer.Transaction
import com.singsin.studio.httpserver.util.GsonInstance
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

object ObjectTransfer : IObjectTransfer {

    private val executorPool: ExecutorService = Executors.newFixedThreadPool(5)

    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")

    private val baseDir: File = File(System.getProperty("user.dir"), "work/transaction").apply { mkdirs() }

    private fun nowStr(): String = LocalDateTime.now().format(formatter)

    /**
     * 保存整个 List<Transaction>，一次性写入文件
     * 返回 200 表示成功，500 表示写入失败
     */
    override fun saveObject(tran: List<Transaction>): Int {
        if (tran.isEmpty()) return 400

        val startTime = System.currentTimeMillis()
        val future: Future<Boolean> = executorPool.submit<Boolean> {
            try {
                saveTransactionListToFile(tran)
                true
            } catch (e: Exception) {
                println("批量写入交易失败: ${e.message}")
                false
            }
        }
        val success = try {
            future.get(30, TimeUnit.SECONDS)
        } catch (e: Exception) {
            println("批量写入超时或异常: ${e.message}")
            false
        }

        val endTime = System.currentTimeMillis()
        println("saveObject 完成写入，耗时: ${endTime - startTime} ms")

        return if (success) 200 else 500
    }

    /**
     * 保存单笔交易
     */
    fun saveObjectForTran(tran: Transaction) {
        executorPool.execute {
            try {
                saveObjectForTranToFile(tran)
            } catch (e: Exception) {
                println("保存交易失败: ${tran.flowNo}, error: ${e.message}")
            }
        }
    }

    /**
     * 批量写入整个 List<Transaction> 到一个文件
     */
    private fun saveTransactionListToFile(tranList: List<Transaction>) {
        val fileName = "batch_${nowStr()}_${UUID.randomUUID()}.json"
        val file = File(baseDir, fileName)
        file.writeText(GsonInstance.gson.toJson(tranList), Charsets.UTF_8)
    }

    /**
     * 写单笔交易文件
     */
    private fun saveObjectForTranToFile(tran: Transaction) {
        val fileName = "${tran.flowNo}_${nowStr()}_${UUID.randomUUID()}.json"
        val file = File(baseDir, fileName)
        file.writeText(GsonInstance.gson.toJson(tran), Charsets.UTF_8)
    }
}

package com.singsin.studio.httpserver.impl

import com.singsin.studio.httpserver.biz.IObjectTransfer
import com.singsin.studio.httpserver.domain.transfer.Transaction
import org.junit.jupiter.api.Test

class ObjectTransferTest {

    @Test
    fun putObjectTransferTest() {
        val objectTransfer: IObjectTransfer = ObjectTransfer

        val saveTransactionCount = 200
        val transactionList = mutableListOf<Transaction>()
        for (i in 1..saveTransactionCount) {
            transactionList.add(
                Transaction(
                    flowNo = "%03d".format(i),
                    accDate = "2026-01-01",
                    storeId = "001",
                    posNo = "00101"
                )
            )
        }
        println("提交 ${transactionList.size} 笔交易到 ObjectTransfer")
        objectTransfer.saveObject(transactionList)
        Thread.sleep(5000)
        println("并发写入完成")
    }
}

package com.singsin.studio.httpserver.biz

import com.singsin.studio.httpserver.domain.transfer.Transaction

interface IObjectTransfer {

    fun saveObject(tran: List<Transaction>): Int
}

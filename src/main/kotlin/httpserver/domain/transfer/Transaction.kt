package com.singsin.studio.httpserver.domain.transfer

data class Transaction(
    /**
     * 交易序号
     */
    val flowNo: String,
    /**
     * 营业日
     */
    var accDate: String,
    /**
     * 门店号
     */
    var storeId: String,
    /**
     * 机号
     */
    var posNo: String,
)

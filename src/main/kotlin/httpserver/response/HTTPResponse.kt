package com.singsin.studio.httpserver.response

data class HTTPResponse(
    val code: Int,
    val message: String,
    val success: Boolean,
    val data: Any? = null
) {
    fun toJson(): String {
        val dataJson = when (data) {
            null -> "null"
            is Number, is Boolean -> data.toString()
            else -> "\"$data\""
        }

        return """
            {
              "code": $code,
              "message": "$message",
              "success": $success,
              "data": $dataJson
            }
        """.trimIndent()
    }
}

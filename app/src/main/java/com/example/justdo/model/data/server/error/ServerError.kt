package com.example.justdo.model.data.server.error

class ServerError(val errorCode: Int, override val message: String) : RuntimeException()
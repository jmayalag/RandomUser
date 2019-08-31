package com.incursio.randomusers.repository.remote.model

data class ResultInfo(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)

data class ApiResult(
    val results: List<User>,
    val info: ResultInfo
)
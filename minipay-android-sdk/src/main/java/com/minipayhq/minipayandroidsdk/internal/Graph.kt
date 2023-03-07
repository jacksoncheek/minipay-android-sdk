package com.minipayhq.minipayandroidsdk.internal

import com.minipayhq.minipayandroidsdk.internal.service.MinipayService
import com.minipayhq.minipayandroidsdk.internal.state.MinipaySdkStateRepo

internal interface Graph {

    val apiKey: String

    val minipayService: MinipayService

    val stateRepo: MinipaySdkStateRepo

    class Builder {

        var apiKey: String? = null

        var minipayService: MinipayService? = null

        var stateRepo: MinipaySdkStateRepo? = null

        fun apiKey(apiKey: String) = apply {
            this.apiKey = apiKey
        }

        fun minipayService(minipayService: MinipayService) = apply {
            this.minipayService = minipayService
        }

        fun stateRepo(stateRepo: MinipaySdkStateRepo) = apply {
            this.stateRepo = stateRepo
        }

        fun build(): Graph {
            requireNotNull(apiKey) {
                "`apiKey` cannot be null"
            }

            requireNotNull(minipayService) {
                "`minipayService` cannot be null"
            }

            requireNotNull(stateRepo) {
                "`stateRepo` cannot be null"
            }

            return GraphImpl(
                GraphImpl.Params(
                    apiKey = apiKey!!,
                    minipayService = minipayService!!,
                    stateRepo = stateRepo!!
                )
            )
        }
    }

    companion object {
        lateinit var instance: Graph internal set
    }
}

private class GraphImpl(
    params: Params
) : Graph {

    init {
        Graph.instance = this
    }

    override val apiKey: String by lazy {
        params.apiKey
    }

    override val minipayService: MinipayService by lazy {
        params.minipayService
    }

    override val stateRepo: MinipaySdkStateRepo by lazy {
        params.stateRepo
    }

    data class Params(
        val apiKey: String,
        val minipayService: MinipayService,
        val stateRepo: MinipaySdkStateRepo
    )
}

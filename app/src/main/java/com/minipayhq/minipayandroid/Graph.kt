package com.minipayhq.minipayandroid

import com.minipayhq.minipayandroidsdk.MinipaySdk

interface Graph {

    val minipaySdk: MinipaySdk

    class Builder {

        var minipaySdk: MinipaySdk? = null

        fun minipaySdk(minipaySdk: MinipaySdk) = apply {
            this.minipaySdk = minipaySdk
        }

        fun build(): Graph {
            requireNotNull(minipaySdk) {
                "`minipaySdk` cannot be null"
            }

            return GraphImpl(
                GraphImpl.Params(
                    minipaySdk = minipaySdk!!,
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

    override val minipaySdk: MinipaySdk by lazy {
        params.minipaySdk
    }

    data class Params(
        val minipaySdk: MinipaySdk
    )
}

package com.minipayhq.minipayandroid

import android.app.Application
import com.minipayhq.minipayandroidsdk.MinipaySdkBuilder

class MainApplication : Application() {

    private val minipaySdk = MinipaySdkBuilder()
        .apiKey(apiKey = "<your-api-key>")
        .build()

    private val graph: Graph by lazy {
        Graph.Builder()
            .minipaySdk(minipaySdk = minipaySdk)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize the object graph before onCreate() finishes
        graph
    }
}

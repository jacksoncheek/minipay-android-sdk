package com.minipayhq.minipayandroid

import android.app.Application
import com.minipayhq.minipayandroidsdk.MinipaySdkBuilder
import com.minipayhq.minipayandroidsdk.MinipaySdkEnvironment

class MainApplication : Application() {

    private val minipaySdk = MinipaySdkBuilder()
        .environment(mode = MinipaySdkEnvironment.TEST) // or MinipaySdkEnvironment.PRODUCTION (for production environment)
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

package com.minipayhq.minipayandroidsdk.internal.state

import com.minipayhq.minipayandroidsdk.MinipaySdkAddAppResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkLoginResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkPostUsageEventResultCallback

internal class MinipaySdkStateRepo {

    var addAppResultCallback: MinipaySdkAddAppResultCallback = { }

    var loginResultCallback: MinipaySdkLoginResultCallback = { }

    var postUsageEventResultCallback: MinipaySdkPostUsageEventResultCallback = { }
}
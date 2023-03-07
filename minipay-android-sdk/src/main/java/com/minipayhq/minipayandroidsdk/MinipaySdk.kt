package com.minipayhq.minipayandroidsdk

import android.content.Context

typealias MinipaySdkAddAppResultCallback = (MinipaySdkResult<Boolean>) -> Unit

typealias MinipaySdkLoginResultCallback = (MinipaySdkResult<String>) -> Unit

typealias MinipaySdkPostUsageEventResultCallback = (MinipaySdkResult<Boolean>) -> Unit

interface MinipaySdk {

    /** Begins the Minipay authorize app flow. No UI.
     *
     * @param customUserId the custom user identifier that you associate with your customer.
     * @param planId the plan identifier that this user is authorizing.
     * @param minipayToken the user's Minipay authentication token returned from the `login` flow.
     * @param callback invoked asynchronously after completion.
     */
    fun authorizeApp(
        customUserId: String,
        planId: String,
        minipayToken: String,
        callback: MinipaySdkAddAppResultCallback
    )

    /** Begins the Minipay login flow.
     *
     * @param appContext required to present the login flow UI.
     * @param callback invoked asynchronously after completion.
     */
    fun login(
        appContext: Context,
        callback: MinipaySdkLoginResultCallback
    )

    /** Begins the Minipay post usage event flow. No UI.
     *
     * @param customUserId the custom user identifier that you associate with your customer.
     * @param planId the plan identifier that this user is authorizing.
     * @param callback invoked asynchronously after completion.
     */
    fun postUsageEvent(
        customUserId: String,
        planId: String,
        callback: MinipaySdkPostUsageEventResultCallback
    )
}

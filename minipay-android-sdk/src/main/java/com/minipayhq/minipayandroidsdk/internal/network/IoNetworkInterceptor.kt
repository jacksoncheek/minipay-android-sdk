package com.minipayhq.minipayandroidsdk.internal.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Catch network [IOException]s
 */
internal class IoExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: Throwable) {
            throw e as? IOException ?: IOException(e)
        }
    }
}

package com.minipayhq.minipayandroidsdk

import com.minipayhq.minipayandroidsdk.internal.MinipaySdkImpl
import com.minipayhq.minipayandroidsdk.internal.network.IoExceptionInterceptor
import com.minipayhq.minipayandroidsdk.internal.service.MinipayService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MinipaySdkBuilder {

    private var apiKey: String? = null

    fun apiKey(apiKey: String) = apply {
        this.apiKey = apiKey
    }

    /** Builds the MinipaySdkImpl. */
    fun build(): MinipaySdk {

        requireNotNull(apiKey) {
            "`apiKey` cannot be null"
        }

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(IoExceptionInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.minipayhq.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        val minipayService = retrofit.create(MinipayService::class.java)

        val params = MinipaySdkImpl.Params(
            apiKey = apiKey!!,
            minipayService = minipayService
        )

        return MinipaySdkImpl(
            params = params
        )
    }
}

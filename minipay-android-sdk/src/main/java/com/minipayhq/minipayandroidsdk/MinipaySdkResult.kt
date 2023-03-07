package com.minipayhq.minipayandroidsdk

/**
 * Monad for an expected/unexpected result from the Minipay SDK.
 */
sealed class MinipaySdkResult<out T> {

    data class Success<out T>(val result: T) : MinipaySdkResult<T>()

    data class Failure(val error: String) : MinipaySdkResult<Nothing>()
}

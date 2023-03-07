package com.minipayhq.minipayandroid

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minipayhq.minipayandroidsdk.MinipaySdk
import com.minipayhq.minipayandroidsdk.MinipaySdkResult

class MainViewModel(
    val minipaySdk: MinipaySdk
) : ViewModel() {

    var minipayToken: String = ""

    var authorizeAppResult: String = ""

    var postUsageEventResult: String = ""

    var error: String = ""

    val isAuthorizeAppFlowEnabled: Boolean
        get() = minipayToken.isNotEmpty()

    val isPostUsageEventFlowEnabled: Boolean
        get() = minipayToken.isNotEmpty()

    fun startLoginFlow(appContext: Context, completion: () -> Unit) {
        minipaySdk.login(appContext = appContext) {
            when (it) {
                is MinipaySdkResult.Failure -> {
                    error = it.error
                }
                is MinipaySdkResult.Success -> {
                    minipayToken = it.result
                }
            }

            completion()
        }
    }

    fun startAuthorizeAppFlow(customUserId: String, planId: String, completion: () -> Unit) {
        error = ""
        authorizeAppResult = ""

        minipaySdk.authorizeApp(customUserId = customUserId, planId = planId, minipayToken = minipayToken) {
            when (it) {
                is MinipaySdkResult.Failure -> {
                    error = it.error
                }
                is MinipaySdkResult.Success -> {
                    authorizeAppResult = if (it.result) "App Authorized" else "App Auth Failed"
                }
            }

            completion()
        }
    }

    fun startPostUsageEventFlow(customUserId: String, planId: String, completion: () -> Unit) {
        error = ""
        postUsageEventResult = ""

        minipaySdk.postUsageEvent(customUserId = customUserId, planId = planId) {
            when (it) {
                is MinipaySdkResult.Failure -> {
                    error = it.error
                }
                is MinipaySdkResult.Success -> {
                    postUsageEventResult = if (it.result) "Access Authorized" else "Access Denied"
                }
            }

            completion()
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val viewModel = when (modelClass) {

                    MainViewModel::class.java -> buildMainViewModel()

                    else -> throw IllegalArgumentException("ViewModel $modelClass is not supported by this factory.")
                }

                return viewModel as T
            }
        }

        private fun buildMainViewModel(): MainViewModel {
            return MainViewModel(
                minipaySdk = Graph.instance.minipaySdk
            )
        }

    }

}
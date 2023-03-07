package com.minipayhq.minipayandroidsdk.internal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minipayhq.minipayandroidsdk.MinipaySdkLoginResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkResult
import com.minipayhq.minipayandroidsdk.internal.Graph
import com.minipayhq.minipayandroidsdk.internal.model.Credentials
import com.minipayhq.minipayandroidsdk.internal.model.request.LoginRequest
import com.minipayhq.minipayandroidsdk.internal.model.response.LoginResponse
import com.minipayhq.minipayandroidsdk.internal.service.MinipayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class LoginViewModel(
    val minipayService: MinipayService,
    val callback: MinipaySdkLoginResultCallback
) : ViewModel() {

    var email: String = ""

    var password: String = ""

    val isLoginButtonEnabled: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()

    fun login(completion: (MinipaySdkResult<String>) -> Unit) {
        val request = LoginRequest(credentials = Credentials(email = email, password = password))

        minipayService.login(request = request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    completion(MinipaySdkResult.Failure(error = "Login failed"))
                    return
                }

                val body = response.body()

                if (body == null) {
                    completion(MinipaySdkResult.Failure(error = "Login failed"))
                    return
                }

                completion(MinipaySdkResult.Success(result = body.token))
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                completion(MinipaySdkResult.Failure(error = t.message ?: "Login failed"))
            }
        })
    }

    companion object {

        /**
         * Creates the correct [LoginViewModel].
         */
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val viewModel = when (modelClass) {

                    LoginViewModel::class.java -> buildLoginViewModel()

                    else -> throw IllegalArgumentException("ViewModel $modelClass is not supported by this factory.")
                }

                return viewModel as T
            }
        }

        private fun buildLoginViewModel(): LoginViewModel {
            return LoginViewModel(
                minipayService = Graph.instance.minipayService,
                callback = Graph.instance.stateRepo.loginResultCallback
            )
        }

    }

}
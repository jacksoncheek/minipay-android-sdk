package com.minipayhq.minipayandroidsdk.internal.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.minipayhq.minipayandroidsdk.MinipaySdkLoginResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkResult
import com.minipayhq.minipayandroidsdk.R
import com.minipayhq.minipayandroidsdk.internal.Graph
import com.minipayhq.minipayandroidsdk.internal.service.MinipayService

internal class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy {
        LoginViewModel.Factory.create(LoginViewModel::class.java)
    }

    private lateinit var title: TextView

    private lateinit var emailAddress: EditText

    private lateinit var password: EditText

    private lateinit var login: Button

    private lateinit var terms: TextView

    private lateinit var error: TextView

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = findViewById(R.id.title)
        emailAddress = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login_button)
        terms = findViewById(R.id.terms)
        error = findViewById(R.id.error)
        progressBar = findViewById(R.id.progress_bar)

        setBindings()
    }

    override fun onDestroy() {
        if (!viewModel.didComplete) {
            viewModel.callback(MinipaySdkResult.Failure(error = "User canceled"))
        }
        super.onDestroy()
    }

    private fun setBindings() {
        emailAddress.addTextChangedListener {
            viewModel.email = it.toString()
            login.isEnabled = viewModel.isLoginButtonEnabled
        }

        password.addTextChangedListener {
            viewModel.password = it.toString()
            login.isEnabled = viewModel.isLoginButtonEnabled
        }

        login.setOnClickListener {
            animateLoading(isLoading = true)
            setError(error = "")

            viewModel.login {
                animateLoading(isLoading = false)

                when (it) {
                    is MinipaySdkResult.Failure -> {
                        setError(error = it.error)
                    }
                    is MinipaySdkResult.Success -> {
                        viewModel.callback(it)
                        finish()
                    }
                }
            }
        }
    }

    private fun animateLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
        title.isVisible = !isLoading
        emailAddress.isVisible = !isLoading
        password.isVisible = !isLoading
        login.isVisible = !isLoading
        terms.isVisible = !isLoading
        error.isVisible = !isLoading
    }

    private fun setError(error: String) {
        this.error.text = error
    }

    /**
     * Arguments for launching a [LoginActivity].
     */
    class Args {

        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

            context.startActivity(intent)
        }
    }
}

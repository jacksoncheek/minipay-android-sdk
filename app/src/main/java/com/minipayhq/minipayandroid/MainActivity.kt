package com.minipayhq.minipayandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        MainViewModel.Factory.create(MainViewModel::class.java)
    }

    private lateinit var title: TextView

    private lateinit var loginButton: Button

    private lateinit var loginState: TextView

    private lateinit var authAppButton: Button

    private lateinit var authAppState: TextView

    private lateinit var postUsageEventButton: Button

    private lateinit var postUsageEventState: TextView

    private lateinit var error: TextView

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = findViewById(R.id.title)
        loginButton = findViewById(R.id.login_button)
        loginState = findViewById(R.id.login_state)
        authAppButton = findViewById(R.id.auth_app_button)
        authAppState = findViewById(R.id.auth_app_state)
        postUsageEventButton = findViewById(R.id.post_usage_event_button)
        postUsageEventState = findViewById(R.id.post_usage_event_state)
        error = findViewById(R.id.error)
        progressBar = findViewById(R.id.progress_bar)

        setBindings()
    }

    private fun setBindings() {
        loginButton.setOnClickListener {
            animateLoading(isLoading = true)
            setError(error = "")

            viewModel.startLoginFlow(appContext = this.applicationContext) {
                update()
                animateLoading(isLoading = false)
            }
        }

        authAppButton.setOnClickListener {
            animateLoading(isLoading = true)
            setError(error = "")

            viewModel.startAuthorizeAppFlow(customUserId = "<your-custom-user-id>", planId = "<your-plan-id>") {
                update()
                animateLoading(isLoading = false)
            }
        }

        postUsageEventButton.setOnClickListener {
            animateLoading(isLoading = true)
            setError(error = "")

            viewModel.startPostUsageEventFlow(customUserId = "<your-custom-user-id>", planId = "<your-plan-id>") {
                update()
                animateLoading(isLoading = false)
            }
        }
    }

    private fun animateLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
        title.isVisible = !isLoading
        loginButton.isVisible = !isLoading
        loginState.isVisible = !isLoading
        authAppButton.isVisible = !isLoading
        authAppState.isVisible = !isLoading
        postUsageEventButton.isVisible = !isLoading
        postUsageEventState.isVisible = !isLoading
        error.isVisible = !isLoading
    }

    private fun update() {
        loginState.text = viewModel.minipayToken

        authAppState.text = viewModel.authorizeAppResult

        postUsageEventState.text = viewModel.postUsageEventResult

        authAppButton.isEnabled = viewModel.isAuthorizeAppFlowEnabled

        postUsageEventButton.isEnabled = viewModel.isPostUsageEventFlowEnabled

        if (viewModel.error.isNotEmpty()) {
            setError(error = viewModel.error)
        }
    }

    private fun setError(error: String) {
        this.error.text = error
    }
}

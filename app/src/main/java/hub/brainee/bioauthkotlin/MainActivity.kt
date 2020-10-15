package hub.brainee.bioauthkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    authStatusTv.text = "Authentication Error: $errString"
                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Error: $errString",
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    authStatusTv.text = "Authentication Success:"

                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Successful",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    authStatusTv.text = "Authentication failed"

                    Toast.makeText(
                        this@MainActivity,
                        "Authentication failed:",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Auth")
            .setSubtitle("Log in using Fingerprint")
            .setNegativeButtonText("Use account password")
            .build()

        authBtn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}
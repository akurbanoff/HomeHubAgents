package ru.hmhub.agents.utils


import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

@Composable
actual fun BiometricAuth(onSuccess: () -> Unit, onError: () -> Unit) {
    val context = LocalContext.current as FragmentActivity
    var executor = context.mainExecutor
    val biometricManager = BiometricManager.from(context)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Вход по отпечатку пальца")
        .setSubtitle("Используйте ваш отпечаток пальца для входа")
        .setNegativeButtonText("Отмена")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    when(biometricManager.canAuthenticate(BIOMETRIC_STRONG)){
        BiometricManager.BIOMETRIC_SUCCESS -> {
            val biometricPrompt = BiometricPrompt(
                context,
                executor,
                object : BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        println("Biometry - успех")
                        onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        println("Biometry - ошибка")
                        onError()
                    }
                }
            )
            biometricPrompt.authenticate(promptInfo)
        }
        else -> {
            Toast.makeText(context, "Вход по отпечатку пальца недоступен.", Toast.LENGTH_LONG).show()
            println("Biometry - Нельзя")
            return
        }
    }

//    when (biometricManager.canAuthenticate()) {
//        BiometricManager.BIOMETRIC_SUCCESS -> {
//            // Биометрическая аутентификация доступна, запустите аутентификацию
//            val promptInfo = BiometricPrompt.Builder(context)
//                .setTitle("Вход по отпечатку пальца")
//                .setSubtitle("Используйте ваш отпечаток пальца для входа")
//                .setNegativeButton(text = "Отмена")
//                .build()
//
//            val biometricPrompt = BiometricPrompt.Builder(context, MainExecutor(), object : BiometricPrompt.AuthenticationCallback() {
//                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                    super.onAuthenticationSucceeded(result)
//                    onSuccess()
//                }
//
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                    super.onAuthenticationError(errorCode, errString)
//                    onError()
//                }
//            })
//
//            biometricPrompt.authenticate(promptInfo)
//        }
//        else -> {
//            // Биометрическая аутентификация недоступна
//            onError()
//        }
//    }
}
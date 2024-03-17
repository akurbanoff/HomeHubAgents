import androidx.compose.ui.window.ComposeUIViewController
import ru.hmhub.agents.App
import platform.UIKit.UIViewController

val sharedPreferencesManager = IOSSharedPreferencesManager()

fun MainViewController(): UIViewController = ComposeUIViewController { App(sharedPreferencesManager) }

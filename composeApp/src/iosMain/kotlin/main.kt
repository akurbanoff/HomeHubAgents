import androidx.compose.ui.window.ComposeUIViewController
import ru.hmhub.agents.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }

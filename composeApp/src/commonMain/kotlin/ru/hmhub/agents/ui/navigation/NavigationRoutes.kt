package ru.hmhub.agents.ui.navigation

sealed class NavigationRoutes(val route: String){
    object LoginScreen: NavigationRoutes("login")
    object RegistrationScreen: NavigationRoutes("registration")
    object NewsScreen: NavigationRoutes("news")
    object NewsDetailScreen: NavigationRoutes("news_detail")
    object RatingScreen: NavigationRoutes("rating")
    object AchievementsScreen: NavigationRoutes("achievements")
    object AchievementsDetailScreen: NavigationRoutes("achievements")
    object ProfileScreen: NavigationRoutes("profile")
    object TradesScreen: NavigationRoutes("trades")
    object TradeDetailScreen: NavigationRoutes("trade")
    object MarketScreen: NavigationRoutes("market")
    object AdminScreen: NavigationRoutes("admin")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgs(vararg args: Int): String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }

}
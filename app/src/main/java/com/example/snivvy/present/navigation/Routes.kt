package com.example.snivvy.present.navigation


//sealed class Routes {
//    data object SplashScreen:Routes()
//    data object WelcomeScreen:Routes()
//    data object UserRegistrationScreen:Routes()
//    data object HomeScreen:Routes()
//    data object UpdateScreen:Routes()
//    data object CommunitiesScreen:Routes()
//    data object CallScreen:Routes()
//
//    data object UserProfileScreen:Routes()
//}
sealed class Routes(val route: String) {
    data object SplashScreen : Routes("splash")
    data object WelcomeScreen : Routes("welcome")
    data object UserRegistrationScreen : Routes("register")
    data object HomeScreen : Routes("home")
    data object UpdateScreen : Routes("update")
    data object CommunitiesScreen : Routes("communities")

    data object CallScreen : Routes("calls")

    data object UserProfileSetScreen : Routes("profile")

    data object SettingScreen : Routes("settings")

    data object ChatScreen : Routes("chat"){
        const val createroute = "chat_screen/{phoneNumber}"
        fun createRoute(phoneNumber: String) = "chat_screen/$phoneNumber"
    }
    data object NewAuthScreen : Routes("new_auth")
}

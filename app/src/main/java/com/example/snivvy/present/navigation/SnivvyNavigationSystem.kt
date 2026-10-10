package com.example.snivvy.present.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snivvy.present.callscreen.CallScreen
import com.example.snivvy.present.communitiesscreen.CommunitiesScreen
import com.example.snivvy.present.homescreen.HomeScreen
import com.example.snivvy.present.profile.UserProfileSetScreen
import com.example.snivvy.present.registrationscreen.AuthScreen
import com.example.snivvy.present.registrationscreen.NewAuthScreen
import com.example.snivvy.present.registrationscreen.UserRegistrationScreen
import com.example.snivvy.present.splashscreen.SplashScreen
import com.example.snivvy.present.updatescreen.UpdateScreen
import com.example.snivvy.present.viewmodel.BaseViewModel
import com.example.snivvy.present.welcomescreen.WelcomeScreen

@Composable
@Preview(showSystemUi = true)
fun SnivvyNavigationSystem(){
    val navController= rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Routes.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

//        composable(Routes.UserRegistrationScreen.route) {
//            AuthScreen(navController)
//        }

        composable(Routes.UserRegistrationScreen.route) {
            NewAuthScreen(navController)      //  REPLACED AuthScreen
        }
        composable(Routes.HomeScreen.route) {
            val baseViewModel: BaseViewModel = hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }

        composable(Routes.UpdateScreen.route) {
            UpdateScreen(navController)
        }

        composable(Routes.CommunitiesScreen.route) {
            CommunitiesScreen(navHostController = navController)
        }
        composable(Routes.CallScreen.route) {
            CallScreen(navHostController = navController)
        }
        composable(Routes.UserProfileSetScreen.route){
            UserProfileSetScreen(navHostController = navController)
        }
    }
}
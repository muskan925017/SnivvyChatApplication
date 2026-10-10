package com.example.snivvy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.snivvy.present.callscreen.CallScreen
import com.example.snivvy.present.communitiesscreen.CommunitiesScreen
import com.example.snivvy.present.homescreen.HomeScreen
import com.example.snivvy.present.navigation.SnivvyNavigationSystem
import com.example.snivvy.present.registrationscreen.UserRegistrationScreen
import com.example.snivvy.present.splashscreen.SplashScreen
import com.example.snivvy.present.updatescreen.TopBar
import com.example.snivvy.present.updatescreen.UpdateScreen
import com.example.snivvy.present.welcomescreen.WelcomeScreen
import com.example.snivvy.ui.theme.SnivvyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnivvyTheme {
               SnivvyNavigationSystem()

            }
        }
    }
}


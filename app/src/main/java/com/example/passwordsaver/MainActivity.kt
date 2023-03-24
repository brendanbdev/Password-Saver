package com.example.passwordsaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.passwordsaver.ui.add_edit_password.AddEditPasswordScreen
import com.example.passwordsaver.ui.theme.PasswordSaverTheme
import com.example.passwordsaver.ui.password_list.PasswordListScreen
import com.example.passwordsaver.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordSaverTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.PASSWORD_LIST
                ) {
                    composable(Routes.PASSWORD_LIST) {
                        PasswordListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_PASSWORD + "?passwordId={passwordId}",
                        arguments = listOf(
                            navArgument(name = "passwordId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditPasswordScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}
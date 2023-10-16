package com.rsnimchenko.simple_arkanoid.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rsnimchenko.simple_arkanoid.game.GameActivity

@Composable
fun NavComponent(score: Int) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val toHistory = { navController.navigate(Screen.History.name) }
    val toMain = {
        navController.navigate(Screen.Main.name) {
            popUpTo(Screen.EndGame.name) { inclusive = true }
        }
    }
    val toBack: () -> Unit = { navController.popBackStack() }
    val toGame = {
        (context as Activity).let {
            it.startActivity(Intent(it, GameActivity::class.java))
            it.finish()
        }
    }
    NavHost(
        navController = navController,
        startDestination = if (score < 0) Screen.Main.name else Screen.EndGame.name
    ) {
        composable(Screen.Main.name) {
            MainScreen(toGame = toGame, toHistory = toHistory)
        }
        composable(Screen.History.name) {
            HistoryScreen(toBack = toBack)
        }
        composable(Screen.EndGame.name) {
            EndGameScreen(score = score, toMain = toMain)
        }
    }
}

enum class Screen {
    Main, History, EndGame
}
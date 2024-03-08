package com.example.evaluacion_u1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion_u1.ui.theme.screens.CalificacionFinal
import com.example.evaluacion_u1.ui.theme.screens.CalificacionUnidad2
import com.example.evaluacion_u1.ui.theme.screens.CargaAcademica
import com.example.evaluacion_u1.ui.theme.screens.DataViewModel
import com.example.evaluacion_u1.ui.theme.screens.PantallaPrincipal
import com.example.evaluacion_u1.ui.theme.screens.mostrarCalifByUnit
import com.example.evaluacion_u1.ui.theme.screens.mostrarDatos
import com.example.evaluacion_u1.ui.theme.screens.mostrarKardex

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val viewModel = DataViewModel()
    NavHost(navController = navController, startDestination = "login"){
        composable(route ="login"){ PantallaPrincipal(navController = navController, viewModel = viewModel) }
        composable(route="data"){ mostrarDatos(navController = navController, viewModel= viewModel)}
        composable(route = "carga"){CargaAcademica(navController = navController, viewModel = viewModel)}
        composable(route = "kardex"){mostrarKardex(navController = navController, viewModel = viewModel)}
        composable(route = "califByUnidad"){mostrarCalifByUnit(navController = navController, viewModel = viewModel)}
        composable(route = "CalificacionFinal"){CalificacionFinal(navController = navController, viewModel = viewModel)}
  ////////////////2
        composable(route = "CalificacionUnidad"){CalificacionUnidad2(navController = navController, viewModel = viewModel)}

    }

}
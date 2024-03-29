package com.example.evaluacion_u1.navigation

sealed class AppScreens (val route: String){
    object loginSice: AppScreens(route = "login_screen")
    object dataScreen: AppScreens(route= "data_screen")
    object cargaAcademic : AppScreens(route = "cargaAcedemic")
    object kardex: AppScreens(route = "kardex")
    object CalifByUnit : AppScreens(route = "califByUnidad")
    object CalificacionFinal : AppScreens(route = "CalificacionFinal")
/////2
    object CalificacionUnidad : AppScreens(route = "CalificacionUnidad")


}
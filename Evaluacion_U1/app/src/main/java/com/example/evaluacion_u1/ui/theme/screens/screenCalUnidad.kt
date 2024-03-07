package com.example.evaluacion_u1.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evaluacion_u1.R
import com.example.evaluacion_u1.data.RetrofitClient
import com.example.evaluacion_u1.model.AlumnoAcademicoResult
import com.example.evaluacion_u1.model.EnvelopeCalUnidad
import com.example.evaluacion_u1.model.EnvelopeKardex
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun GetCalUnidad(
    context: Context,
    navController: NavController,
    viewModel: DataViewModel
) {
    val service = RetrofitClient(context).retrofitService3
    val bodyCalUnidad = CalUnidadRequestBody()
    service.getCalUnidad(bodyCalUnidad).enqueue(object : Callback<EnvelopeCalUnidad> {
        override fun onResponse(call: Call<EnvelopeCalUnidad>, response: Response<EnvelopeCalUnidad>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val CalUnidadResultJson: String? =
                    envelope?.bodyCalUnidad?.getCalifUnidadesByAlumnoResponse?.getCalifUnidadesByAlumnoResult

                // Deserializa la cadena JSON a AlumnoAcademicoResult
                val json = Json { ignoreUnknownKeys = true }
                val alumnoAcademicoResult: AlumnoAcademicoResult? =
                    CalUnidadResultJson?.let { json.decodeFromString(it) }

                Log.w("exito", "se obtuvo el perfil 2: ${alumnoAcademicoResult}")
                val alumnoAcademicoResultJson = Json.encodeToString(alumnoAcademicoResult)
                viewModel.alumnoAcademicoResult = alumnoAcademicoResult
                navController.navigate("data")
            } else {
                showError(
                    context,
                    "Error al obtener el perfil académico. Código de respuesta: ${response.code()}"
                )
            }
        }
        private fun CalUnidadRequestBody(): RequestBody {
            return """
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
              <aluLineamiento>3</aluLineamiento>
            </getAllKardexConPromedioByAlumno>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
        }
        override fun onFailure(call: Call<EnvelopeCalUnidad>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud del perfil académico")
        }


    })
}
private fun CalUnidadRequestBody(): RequestBody {
    return """
           <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getCalifUnidadesByAlumnoResponse xmlns="http://tempuri.org/">
              <getCalifUnidadesByAlumnoResult>string</getCalifUnidadesByAlumnoResult>
            </getCalifUnidadesByAlumnoResponse>
          </soap:Body>
        </soap:Envelope>
        """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}
private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Composable
fun MostrarCalUnidad(navController: NavController, viewModel: DataViewModel){
    val CalUnidad = viewModel.CalificacionPorUnidad
    Image(
        painter = painterResource(id = R.drawable.fondodate),
        contentDescription = "My background image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    if (CalUnidad != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Card(modifier = Modifier.padding(30.dp).size(600.dp,45.dp)){
                Text(text = "Observaciones: ${CalUnidad.Observaciones}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }
            Card(modifier = Modifier.padding(30.dp).size(600.dp,45.dp)){
                Text(text = "C1: ${CalUnidad.C1}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C2: ${CalUnidad.C2}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C3: ${CalUnidad.C3}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C4: ${CalUnidad.C4}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C5: ${CalUnidad.C5}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C6: ${CalUnidad.C6}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C7: ${CalUnidad.C7}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C8: ${CalUnidad.C8}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C9: ${CalUnidad.C9}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C10: ${CalUnidad.C10}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C11: ${CalUnidad.C11}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C12: ${CalUnidad.C12}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = "C13: ${CalUnidad.C13}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }
            Card(modifier = Modifier.padding(30.dp).size(600.dp,45.dp)){
                Text(text = "Unidades Activas: ${CalUnidad.UnidadesActivas}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }
            Card(modifier = Modifier.padding(30.dp).size(600.dp,45.dp)){
                Text(text = "Materia: ${CalUnidad.Materia}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }
            Card(modifier = Modifier.padding(30.dp).size(600.dp,45.dp)){
                Text(text = "Grupo: ${CalUnidad.Grupo}",modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
            }


            // Agrega más campos aquí
            Spacer(modifier = Modifier.padding(30.dp))
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController.popBackStack() }) {
                Text(text = "Carga Academica")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController.popBackStack() }) {
                Text(text = "Cerrar sesion")
            }
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudo obtener el perfil académico.")
        }
    }
}
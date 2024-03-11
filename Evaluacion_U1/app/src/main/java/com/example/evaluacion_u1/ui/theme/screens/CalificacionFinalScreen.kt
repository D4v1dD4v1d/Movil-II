package com.example.evaluacion_u1.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion_u1.data.RetrofitClient
import com.example.evaluacion_u1.model.AllCalificacionFinal
import com.example.evaluacion_u1.model.CargaAcademicaItem
import com.example.evaluacion_u1.model.Envelope2
import com.example.evaluacion_u1.model.Envelope5
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun CalificacionFinal(navController: NavController, viewModel: DataViewModel) {
    val CalificacionFinal = viewModel.calificacionfinal
    Column {
        Text(
            text = "Calificacion Final\n ",
            modifier = Modifier
                .padding(horizontal =  90.dp),
            fontSize = 24.sp
        )
    }

    if (CalificacionFinal != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(vertical = 30.dp)
        ) {
            items(CalificacionFinal) { AllCalificacionFinal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .size(1050.dp, 200.dp)
                    )
                    {
                        Text(
                            text = "Materia: ${AllCalificacionFinal.materia}",
                            modifier = Modifier.weight(1.4f)
                        )
                        Text(
                            text = "Calificacion: ${AllCalificacionFinal.calif}",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Grupo: ${AllCalificacionFinal.grupo}",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Creditos: ${AllCalificacionFinal.acred}",
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "Observaciones: ${AllCalificacionFinal.Observaciones}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

fun getCalificacionFinal(
    context: Context,
    navController: NavController,
    viewModel: DataViewModel
){
    val service = RetrofitClient(context).retrofitService5
    val bodyAcademico = cargarRequestBody()
    service.getAllCalifFinal(bodyAcademico).enqueue(object : Callback<Envelope5> {
        override fun onResponse(call: Call<Envelope5>, response: Response<Envelope5>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val alumnoResultJson: String? =
                    envelope?.body5?.getAllCalifFinalByAlumnosResponse?.getAllCalifFinalByAlumnosResult

                // Deserializa la cadena JSON a AlumnoAcademicoResult
                val json = Json { ignoreUnknownKeys = true }
                val allCalificacionfinalItem: List<AllCalificacionFinal>? =
                    alumnoResultJson?.let { json.decodeFromString(it) }

                Log.w("exito", "se obtuvo el perfil 2: ${allCalificacionfinalItem}")
                val alumnoAcademicoResultJson = Json.encodeToString(allCalificacionfinalItem)
                viewModel.calificacionfinal = allCalificacionfinalItem
                navController.navigate("CalificacionFinal")
            } else {
                showError(
                    context,
                    "Error al obtener el perfil académico. Código de respuesta: ${response.code()}"
                )
            }
        }
        override fun onFailure(call: Call<Envelope5>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud del perfil académico")

        }


    })
}


private fun cargarRequestBody(): RequestBody {
    return """
    <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Body>
            <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                <bytModEducativo>2</bytModEducativo>
            </getAllCalifFinalByAlumnos>
        </soap:Body>
    </soap:Envelope>
        """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}
private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

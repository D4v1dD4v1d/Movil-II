package com.example.evaluacion_u1.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
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
fun MostrarCalUnidad(navController: NavController, viewModel: DataViewModel){

}
package com.example.evaluacion_u1.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.evaluacion_u1.data.RetrofitClient
import com.example.evaluacion_u1.model.AlumnoAcademicoResult
import com.example.evaluacion_u1.model.EnvelopeCalFinal
import com.example.evaluacion_u1.model.EnvelopeKardex
import com.example.evaluacion_u1.network.CalFinal
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun GetCalFinal(
    context: Context,
    navController: NavController,
    viewModel: DataViewModel
) {
    val service = RetrofitClient(context).retrofitService4
    val bodyCalFinal = CalFinalRequestBody()
    service.getCalFinal(bodyCalFinal).enqueue(object : Callback<EnvelopeCalFinal> {
        override fun onResponse(call: Call<EnvelopeCalFinal>, response: Response<EnvelopeCalFinal>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val CalFinalResultJson: String? =
                    envelope?.bodyCalFinal?.getAllCalifFinalByAlumnosResponse?.getAllCalifFinalByAlumnosResult

                // Deserializa la cadena JSON a AlumnoAcademicoResult
                val json = Json { ignoreUnknownKeys = true }
                val alumnoAcademicoResult: AlumnoAcademicoResult? =
                    CalFinalResultJson?.let { json.decodeFromString(it) }

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

        override fun onFailure(call: Call<EnvelopeCalFinal>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud del perfil académico")
        }
    })
}
private fun CalFinalRequestBody(): RequestBody {
    return """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getAllCalifFinalByAlumnosResponse xmlns="http://tempuri.org/">
      <getAllCalifFinalByAlumnosResult>string</getAllCalifFinalByAlumnosResult>
    </getAllCalifFinalByAlumnosResponse>
  </soap:Body>
</soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}
private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Composable
fun MostrarCalFinal(navController: NavController, viewModel: DataViewModel){

}
package com.example.evaluacion_u1.ui.theme.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.evaluacion_u1.data.RetrofitClient
import com.example.evaluacion_u1.model.AlumnoAcademicoResult
import com.example.evaluacion_u1.model.EnvelopeCarga
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun GetCargaAcademica(
    context: Context,
    navController: NavController,
    viewModel: DataViewModel
) {
    val service = RetrofitClient(context).retrofitService5
    val bodyCarga = CargaAcademicaRequestBody()
    service.getCargaAcademica(bodyCarga).enqueue(object : Callback<EnvelopeCarga> {
        override fun onResponse(call: Call<EnvelopeCarga>, response: Response<EnvelopeCarga>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val CargaResultJson: String? =
                    envelope?.bodyCarga?.getCargaAcademicaByAlumnoResponse?.getCargaAcademicaByAlumnoResult

                // Deserializa la cadena JSON a AlumnoAcademicoResult
                val json = Json { ignoreUnknownKeys = true }
                val alumnoAcademicoResult: AlumnoAcademicoResult? =
                    CargaResultJson?.let { json.decodeFromString(it) }

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

        override fun onFailure(call: Call<EnvelopeCarga>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud del perfil académico")
        }
    })
}
private fun CargaAcademicaRequestBody(): RequestBody {
    return """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getCargaAcademicaByAlumnoResponse xmlns="http://tempuri.org/">
      <getCargaAcademicaByAlumnoResult>string</getCargaAcademicaByAlumnoResult>
    </getCargaAcademicaByAlumnoResponse>
  </soap:Body>
</soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}
private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Composable
fun MostrarCargaAcademica(navController: NavController, viewModel: DataViewModel){

}
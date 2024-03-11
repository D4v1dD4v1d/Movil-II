package com.example.evaluacion_u1.model

import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "Envelope", strict = false)
data class Envelope5 @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var body5: Body5 = Body5()
)
@Root(name = "Body", strict = false)
data class Body5 @JvmOverloads constructor(
    @field:Element(name = "getAllCalifFinalByAlumnosResponse", required = false)
    var getAllCalifFinalByAlumnosResponse: GetAllCalifFinalByAlumnosResponse = GetAllCalifFinalByAlumnosResponse()
)

@Root(name = "getAllCalifFinalByAlumnosResponse", strict = false)
data class GetAllCalifFinalByAlumnosResponse @JvmOverloads constructor(
    @field:Element(name = "getAllCalifFinalByAlumnosResult", required = false)
    var getAllCalifFinalByAlumnosResult: String = ""
)


@Serializable
data class AllCalificacionFinal(
    val calif: Int,
    val acred: String,
    val grupo: String,
    val materia: String,
    val Observaciones: String
)
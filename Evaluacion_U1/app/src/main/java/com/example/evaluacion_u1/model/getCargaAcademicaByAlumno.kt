package com.example.evaluacion_u1.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Envelope", strict = false)
data class EnvelopeCarga@JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyCarga: BodyCarga = BodyCarga()
)

@Root(name = "Body", strict = false)
data class BodyCarga @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResponse", required = false)
    var getCargaAcademicaByAlumnoResponse: getCargaAcademicaByAlumnoResponse = getCargaAcademicaByAlumnoResponse()
)

@Root(name = "getCargaAcademicaByAlumnoResponse", strict = false)
data class getCargaAcademicaByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResult", required = false)
    var getCargaAcademicaByAlumnoResult: String = ""
)

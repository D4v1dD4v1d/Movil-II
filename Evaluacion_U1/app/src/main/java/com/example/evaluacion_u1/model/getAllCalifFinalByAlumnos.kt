package com.example.evaluacion_u1.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Envelope", strict = false)
data class EnvelopeCalUnidad @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyCalUnidad: BodyCalUnidad = BodyCalUnidad()
)

@Root(name = "Body", strict = false)
data class BodyCalUnidad @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResponse ", required = false)
    var getCalifUnidadesByAlumnoResponse : getCalifUnidadesByAlumnoResponse  = getCalifUnidadesByAlumnoResponse ()
)

@Root(name = "getAllKardexConPromedioByAlumnoResponse", strict = false)
data class getCalifUnidadesByAlumnoResponse  @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResult", required = false)
    var getCalifUnidadesByAlumnoResult: String = ""
)


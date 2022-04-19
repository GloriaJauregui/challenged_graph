package pruebas.gloriajaureguiapp.apiservice.entities

import com.google.gson.annotations.SerializedName

/**
 * Entidad para el mapeo de datos de la respuesta del servicio.
 */
data class ListTopResultset(
    @SerializedName("issueId")
    var issueId: String? = null,
    @SerializedName("percentageChange")
    var percentageChange: Double = 0.0,
    @SerializedName("lastPrice")
    var lastPrice: Double = 0.0,
    @SerializedName("riseLowTypeId")
    var riseLowTypeId: ValueType
)

enum class ValueType(val title: String) {
    @SerializedName("1")
    TYPE_LOW("Bajas"),
    @SerializedName("2")
    TYPE_RISE("Alzas"),
    @SerializedName("3")
    TYPE_VOLUME("Volumen")
}
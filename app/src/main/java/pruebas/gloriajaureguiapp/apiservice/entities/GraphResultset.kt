package pruebas.gloriajaureguiapp.apiservice.entities

import com.google.gson.annotations.SerializedName

/**
 * Entidad para el mapeo de datos de la respuesta del servicio
 * con los datos de la gráfica.
 */
data class GraphResultset(
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("percentageChange")
    var percentageChange: Double = 0.0,
    @SerializedName("volume")
    var volume: Int = 0,
    @SerializedName("change")
    var change: Double = 0.0
)
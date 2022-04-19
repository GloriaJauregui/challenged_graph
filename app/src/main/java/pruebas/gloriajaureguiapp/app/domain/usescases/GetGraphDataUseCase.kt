package pruebas.gloriajaureguiapp.app.domain.usescases

import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository

/**
 * Obtiene los datos para la gráfica.
 * @param dataRepository [DataRepository].
 */
class GetGraphDataUseCase(private val dataRepository: DataRepository) {

    suspend fun execute() =
        dataRepository.getGraphData()
}
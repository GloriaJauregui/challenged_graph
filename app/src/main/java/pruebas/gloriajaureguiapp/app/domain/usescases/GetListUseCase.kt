package pruebas.gloriajaureguiapp.app.domain.usescases

import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository

/**
 * Obtiene la lista de top.
 * @param dataRepository [DataRepository].
 */
class GetListUseCase (private val dataRepository: DataRepository) {

    suspend fun execute() =
        dataRepository.getListTop()
}
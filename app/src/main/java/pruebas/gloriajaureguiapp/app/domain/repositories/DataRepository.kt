package pruebas.gloriajaureguiapp.app.domain.repositories

import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.mvi.AppResponse

interface DataRepository {
    /**
     * Obtiene la lista de top.
     * @return [AppResponse<ListTopResultset>] Lista de tops.
     */
    suspend fun getListTop(): AppResponse<List<ListTopResultset>?>

    /**
     * Obtiene los datos necesarios para la gráfica.
     * @return [AppResponse<GraphResultset>] Lista de datos.
     */
    suspend fun getGraphData(): AppResponse<List<GraphResultset>?>
}
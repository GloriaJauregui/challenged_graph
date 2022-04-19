package pruebas.gloriajaureguiapp.app.data.repositories

import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.app.domain.datasources.RemoteDataSource
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.mvi.AppResponse

/**
 * Contiene todas las funcionalidades y llamadas referentes a los post.
 */
class RepositoryImp(val remoteDataSource: RemoteDataSource) : DataRepository {

    override suspend fun getListTop()
            : AppResponse<List<ListTopResultset>?> =
        when (val response =
            remoteDataSource.getListTop()) {
            is AppResponse.Failed -> {
                AppResponse.Failed(response.errorMessage)
            }
            is AppResponse.Successful -> {
                AppResponse.Successful(response.content)
            }
        }

    override suspend fun getGraphData()
            : AppResponse<List<GraphResultset>?> =
        when (val response =
            remoteDataSource.getGraphData()) {
            is AppResponse.Failed -> {
                AppResponse.Failed(response.errorMessage)
            }
            is AppResponse.Successful -> {
                AppResponse.Successful(response.content)
            }
        }
}
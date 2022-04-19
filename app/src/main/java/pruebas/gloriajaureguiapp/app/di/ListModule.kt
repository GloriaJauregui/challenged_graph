package pruebas.gloriajaureguiapp.app.di

import android.os.Bundle
import pruebas.gloriajaureguiapp.app.data.datasource.RemoteDataSourceImp
import pruebas.gloriajaureguiapp.app.data.repositories.RepositoryImp
import pruebas.gloriajaureguiapp.app.domain.datasources.RemoteDataSource
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.app.domain.usescases.GetGraphDataUseCase
import pruebas.gloriajaureguiapp.app.domain.usescases.GetListUseCase
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphViewModelFactory
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListViewModelFactory

object ListModule {

    /**
     * Proporciona una fábrica de construcción del viewModel de post.
     * @return [providesListViewModelFactory] fabrica de ListViewModel.
     * */
    fun providesListViewModelFactory(
        defaultArgs: Bundle? = null
    ): ListViewModelFactory {
        return ListViewModelFactory(
            providesGetListUseCase(
                providesPostRepository(
                    providesRemoteDataSource()
                )
            )
        )
    }

    /**
     * Proporciona el caso de uso para obtener la lista.
     */
    private fun providesGetListUseCase(repository: DataRepository): GetListUseCase {
        return GetListUseCase(repository)
    }

    /**
     * Proporciona una fábrica de construcción del viewModel de post.
     * @return [providesGraphDataViewModelFactory] fabrica de ListViewModel.
     * */
     fun providesGraphDataViewModelFactory(
        defaultArgs: Bundle? = null
    ): GraphViewModelFactory {
        return GraphViewModelFactory(
            providesGetGraphDataUseCase(
                providesPostRepository(
                    providesRemoteDataSource()
                )
            )
        )
    }

    /**
     * Proporciona el caso de uso para obtener los datos para la gráfica.
     */
    private fun providesGetGraphDataUseCase(repository: DataRepository): GetGraphDataUseCase {
        return GetGraphDataUseCase(repository)
    }


    /**
     * Proporciona una instancia del repositorio.
     */
    private fun providesPostRepository(
        remoteDataSource: RemoteDataSource
    ): DataRepository {
        return RepositoryImp(remoteDataSource)
    }

    /**
     * Proporciona una instancia del recurso de datos remotos.
     */
    private fun providesRemoteDataSource(): RemoteDataSource =
        RemoteDataSourceImp()

}
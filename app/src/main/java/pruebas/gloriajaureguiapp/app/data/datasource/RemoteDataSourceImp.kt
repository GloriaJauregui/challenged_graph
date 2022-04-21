package pruebas.gloriajaureguiapp.app.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.apiservice.service.APIService
import pruebas.gloriajaureguiapp.app.domain.datasources.RemoteDataSource
import pruebas.gloriajaureguiapp.mvi.AppResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Contiene las llamadas a los servicios.
 */
class RemoteDataSourceImp : RemoteDataSource {

    private fun getRetrofitListTop(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/b4eb963c-4aee-4b60-a378-20cb5b00678f/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitGraphData(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override suspend fun getListTop() =
        withContext<AppResponse<List<ListTopResultset>?>>(
            Dispatchers.IO
        ) {
            val call =
                getRetrofitListTop().create(APIService::class.java).getListTop("").execute()
            if (call.isSuccessful) {
                AppResponse.Successful(call.body())
            } else {
                AppResponse.Failed(call.message())
            }
        }

    override suspend fun getGraphData() =
        withContext<AppResponse<List<GraphResultset>?>>(
            Dispatchers.IO
        ) {
            val call =
                getRetrofitGraphData().create(APIService::class.java).getGraphData("").execute()
            if (call.isSuccessful) {
                AppResponse.Successful(call.body())
            } else {
                AppResponse.Failed(call.message())
            }
        }
}
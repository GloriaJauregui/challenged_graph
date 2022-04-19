package pruebas.gloriajaureguiapp.apiservice.service

import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getListTop(@Url url:String): Call<List<ListTopResultset>>

    @GET
    fun getGraphData(@Url url:String): Call<List<GraphResultset>>
}
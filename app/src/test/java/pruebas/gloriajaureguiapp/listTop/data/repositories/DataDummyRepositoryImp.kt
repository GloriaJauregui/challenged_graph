package pruebas.gloriajaureguiapp.listTop.data.repositories

import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ValueType
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.mvi.AppResponse

object DataDummyRepositoryImp : DataRepository {

    override suspend fun getListTop(): AppResponse<List<ListTopResultset>?> {
        var list: ArrayList<ListTopResultset> = arrayListOf()
        list.add(ListTopResultset("1",1.1,3.2,ValueType.TYPE_LOW))
        list.add(ListTopResultset("2",2.1,3.2,ValueType.TYPE_RISE))
        list.add(ListTopResultset("3",3.1,3.2,ValueType.TYPE_VOLUME))
        return AppResponse.Successful(list)
    }

    override suspend fun getGraphData(): AppResponse<List<GraphResultset>?> {
        var graph: ArrayList<GraphResultset> = arrayListOf()
        graph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 1.1,1.1,1,1.1))
        graph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 2.1,2.1,1,2.1))
        graph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 3.1,3.1,1,3.1))
        return AppResponse.Successful(graph)
    }
}


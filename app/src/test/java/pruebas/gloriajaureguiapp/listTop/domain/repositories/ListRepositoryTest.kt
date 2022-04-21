package pruebas.gloriajaureguiapp.listTop.domain.repositories

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert
import org.junit.Test
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ValueType
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.mvi.AppResponse

class ListRepositoryTest {
    private var dataRepository: DataRepository = mockk()

    companion object {
        const val FAIL_MESSAGE: String = "No sé está devolviendo la respuesta Successful"
    }

    @Test
    fun `Test get list`() {
        runBlocking {
            coEvery { dataRepository.getListTop() } returns AppResponse.Successful(
                GetListTop()
            )
            when (val detailResult = dataRepository.getListTop()) {
                is AppResponse.Successful -> {
                    detailResult.content shouldBeEqualTo GetListTop()
                }
                else -> {
                    Assert.fail(FAIL_MESSAGE)
                }
            }
        }
    }

    @Test
    fun `Test get data graph`() {
        runBlocking {
            coEvery { dataRepository.getGraphData() } returns AppResponse.Successful(
                GetDateGraph()
            )
            when (val detailResult = dataRepository.getGraphData()) {
                is AppResponse.Successful -> {
                    detailResult.content shouldBeEqualTo GetDateGraph()
                }
                else -> {
                    Assert.fail(FAIL_MESSAGE)
                }
            }
        }
    }

    fun GetDateGraph(): ArrayList<GraphResultset> {
        var dataGraph: ArrayList<GraphResultset> = arrayListOf()
        dataGraph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 1.1, 1.1, 1, 1.1))
        dataGraph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 2.1, 2.1, 1, 2.1))
        dataGraph.add(GraphResultset("2020-08-18T00:01:43.633-05:00", 3.1, 3.1, 1, 3.1))
        return dataGraph
    }

    fun GetListTop(): ArrayList<ListTopResultset> {
        var list: ArrayList<ListTopResultset> = arrayListOf()
        list.add(ListTopResultset("1", 1.1, 3.2, ValueType.TYPE_LOW))
        list.add(ListTopResultset("2", 2.1, 3.2, ValueType.TYPE_RISE))
        list.add(ListTopResultset("3", 3.1, 3.2, ValueType.TYPE_VOLUME))
        return list
    }

}
package pruebas.gloriajaureguiapp.listTop.domain.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Test
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.app.domain.usescases.GetGraphDataUseCase
import pruebas.gloriajaureguiapp.mvi.AppResponse

class GetGraphDataUseCaseTest {
    private val mockedRepository = mockk<DataRepository>()
    private val useCase: GetGraphDataUseCase = mockk()

    companion object {
        const val FAIL_MESSAGE: String = "No sé está devolviendo la respuesta Successful"
    }

    @Test
    fun `Test execute with mocked return`() {
        coEvery { useCase.execute() } returns AppResponse.Successful(
            GetDateGraph()
        )
        runBlocking {
            val response = useCase.execute()
            response shouldBeInstanceOf AppResponse.Successful::class
            when (response) {
                is AppResponse.Successful -> {
                    response.content shouldBeEqualTo GetDateGraph()
                }
                else -> {
                    Assert.fail(FAIL_MESSAGE)
                }
            }

        }
    }

    @Test
    fun `Test execute with mocked repository news seen`() {
        val repositoryUseCase = GetGraphDataUseCase(mockedRepository)
        coEvery { mockedRepository.getGraphData() } returns AppResponse.Successful(
            GetDateGraph()
        )
        runBlocking {
            val response = repositoryUseCase.execute()
            response shouldBeEqualTo GetDateGraph()
            when (response) {
                is AppResponse.Successful -> {
                    response.content shouldBeEqualTo GetDateGraph()
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
}
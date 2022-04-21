package pruebas.gloriajaureguiapp.listTop.domain.usecases

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert
import org.junit.Test
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.app.domain.usescases.GetGraphDataUseCase
import pruebas.gloriajaureguiapp.app.domain.usescases.GetListUseCase
import pruebas.gloriajaureguiapp.mvi.AppResponse

class GetListUseCaseTest {
    private val mockedRepository = mockk<DataRepository>()
    private val useCase: GetListUseCase = mockk()
    private var listTop: ArrayList<ListTopResultset> = arrayListOf()

    companion object {
        const val FAIL_MESSAGE: String = "No sé está devolviendo la respuesta Successful"
    }

    @Test
    fun `Test execute with mocked return`() {
        coEvery { useCase.execute() } returns AppResponse.Successful(
            listTop
        )
        runBlocking {
            val response = useCase.execute()
            response shouldBeInstanceOf AppResponse.Successful::class
            when (response) {
                is AppResponse.Successful -> {
                    response.content shouldBeEqualTo listTop
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
        coEvery { mockedRepository.getListTop() } returns AppResponse.Successful(
            listTop
        )
        runBlocking {
            val response = repositoryUseCase.execute()
            response shouldBeEqualTo listTop
            when (response) {
                is AppResponse.Successful -> {
                    response.content shouldBeEqualTo listTop
                }
                else -> {
                    Assert.fail(FAIL_MESSAGE)
                }
            }
        }
    }
}
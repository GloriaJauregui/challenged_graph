package pruebas.gloriajaureguiapp.listTop.presentation


import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import io.mockk.justRun
import io.mockk.slot
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import pruebas.gloriajaureguiapp.app.TestApplication
import pruebas.gloriajaureguiapp.app.domain.repositories.DataRepository
import pruebas.gloriajaureguiapp.app.domain.usescases.GetListUseCase
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListViewModel
import pruebas.gloriajaureguiapp.listTop.data.repositories.DataDummyRepositoryImp
import pruebas.gloriajaureguiapp.mvi.Base

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [28])
class ListViewModelTest {
    private lateinit var viewModel: ListViewModel
    private lateinit var observer: Observer<ListModel>
    private lateinit var dummyRepository: DataRepository
    private lateinit var spykRepo: DataRepository
    private val uiState = slot<ListModel>()
    private lateinit var listUseCaseMock: GetListUseCase

    fun setup() {
        val testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
        dummyRepository = DataDummyRepositoryImp
        spykRepo = spyk(dummyRepository)
        listUseCaseMock = GetListUseCase(spykRepo)
        viewModel = ListViewModel(
            listUseCaseMock, SavedStateHandle(
                mapOf(
                    Pair(
                        Base.SAVED_STATE,
                        ListModel.State()
                    )
                )
            ))

        observer = spyk(Observer { })
        justRun {
            observer.onChanged(capture(uiState))
        }
    }

    @Test
    fun `Test get list`() {
        viewModel.onEvent(ListModel.Event.OnGetList)
        val resultState = uiState.captured
        resultState shouldBe ListModel.Result.Success.OnResult
    }
}
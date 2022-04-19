package pruebas.gloriajaureguiapp.app.presentation.graph

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pruebas.gloriajaureguiapp.app.domain.usescases.GetGraphDataUseCase
import pruebas.gloriajaureguiapp.mvi.Base

class GraphViewModelFactory(
    private val getGraphDataUseCase: GetGraphDataUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(GraphViewModel::class.java) -> {
                return GraphViewModel(
                    getGraphDataUseCase,
                    SavedStateHandle(
                        mapOf(
                            Pair(
                                Base.SAVED_STATE,
                                GraphModel.State()
                            )
                        )
                    )
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
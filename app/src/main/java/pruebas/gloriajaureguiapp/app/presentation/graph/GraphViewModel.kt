package pruebas.gloriajaureguiapp.app.presentation.graph

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.app.domain.usescases.GetGraphDataUseCase
import pruebas.gloriajaureguiapp.mvi.AppResponse
import pruebas.gloriajaureguiapp.mvi.BaseViewModel
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.Event
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.Result
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.SideEffect
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.State

/**
 * Llamado a los eventos.
 * @param getGraphDataUseCase[GetGraphDataUseCase] obtiene los datos para la gráfica.
 */
class GraphViewModel(
    private val getGraphDataUseCase: GetGraphDataUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<State, Event, SideEffect, Result>(
    savedStateHandle
) {
    override fun onEvent(event: Event) {
        when (event) {
            is Event.OnGetData -> {
                getGraphData()
            }
        }
    }

    /**
     * Obtiene los datos para la gráfica.
     */
    private fun getGraphData() {
        emitLoading(true)
        viewModelScope.launch {
            when (val response = getGraphDataUseCase.execute()) {
                is AppResponse.Failed -> {
                    onResult(Result.Failed.OnResult)
                    emitSideEffect(SideEffect.ErrorShowData(response.errorMessage))
                }
                is AppResponse.Successful -> {
                    onResult(Result.Success.OnResult)
                    emitSideEffect(SideEffect.ShowData(response.content as ArrayList<GraphResultset>))
                }
            }
        }
    }


    override fun emitLoading(isLoading: Boolean, loadingTextRes: Int?) {
        emitState(state.copy(isLoading = isLoading))
    }

    override fun onResult(result: Result) {
        val stateToEmit = when (result) {
            Result.Failed.OnResult -> state.copy(isLoading = false)
            Result.Success.OnResult -> state.copy(isLoading = false)
            else -> state.copy(isLoading = false)
        }
        emitState(stateToEmit)
    }
}
package pruebas.gloriajaureguiapp.app.presentation.listTop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset
import pruebas.gloriajaureguiapp.apiservice.entities.ValueType
import pruebas.gloriajaureguiapp.app.domain.usescases.GetListUseCase
import pruebas.gloriajaureguiapp.mvi.AppResponse
import pruebas.gloriajaureguiapp.mvi.BaseViewModel
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.Event
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.Result
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.SideEffect
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.State

/**
 * Llamado a los eventos.
 * @param getListUseCase[GetListUseCase] obtiene la lista.
 */
class ListViewModel(
    private val getListUseCase: GetListUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<State, Event, SideEffect, Result>(
    savedStateHandle
) {
    private var list: ArrayList<ItemList> = arrayListOf()
    private var listContent: ArrayList<ListTopResultset> = arrayListOf()

    override fun onEvent(event: Event) {
        when (event) {
            is Event.OnGetList -> {
                getList()
            }
        }
    }

    private fun getList() {
        emitLoading(true)
        viewModelScope.launch {
            when (val response = getListUseCase.execute()) {
                is AppResponse.Failed -> {
                    onResult(Result.Failed.OnResult)
                    emitSideEffect(SideEffect.ErrorShowList(response.errorMessage))
                }
                is AppResponse.Successful -> {
                    list.clear()
                    listContent.clear()
                    listContent = response.content as ArrayList<ListTopResultset>
                    val contentRise =
                        response.content?.filter {
                            it.riseLowTypeId == ValueType.TYPE_RISE
                        }
                    if (contentRise.isNotEmpty()) {
                        list.add(ItemList.Header(ValueType.TYPE_RISE.title))
                        for (i in contentRise) {
                            list.add(ItemList.Item(i))
                        }
                    }
                    val contentLow =
                        response.content?.filter {
                            it.riseLowTypeId == ValueType.TYPE_LOW
                        }
                    if (contentLow.isNotEmpty()) {
                        list.add(ItemList.Header(ValueType.TYPE_LOW.title))
                        for (i in contentLow) {
                            list.add(ItemList.Item(i))
                        }
                    }
                    val contentVolume =
                        response.content?.filter {
                            it.riseLowTypeId == ValueType.TYPE_RISE
                        }
                    if (contentVolume.isNotEmpty()) {
                        list.add(ItemList.Header(ValueType.TYPE_VOLUME.title))
                        for (i in contentVolume) {
                            list.add(ItemList.Item(i))
                        }
                    }
                    onResult(Result.Success.OnResult)
                    emitSideEffect(SideEffect.ShowList(list))
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
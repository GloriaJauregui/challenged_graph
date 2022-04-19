package pruebas.gloriajaureguiapp.app.presentation.listTop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pruebas.gloriajaureguiapp.app.domain.usescases.GetListUseCase
import pruebas.gloriajaureguiapp.mvi.Base

class ListViewModelFactory(
    private val getListUseCase: GetListUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                return ListViewModel(
                    getListUseCase,
                    SavedStateHandle(
                        mapOf(
                            Pair(
                                Base.SAVED_STATE,
                                ListModel.State()
                            )
                        )
                    )
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
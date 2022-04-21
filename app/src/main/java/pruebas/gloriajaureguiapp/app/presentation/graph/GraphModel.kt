package pruebas.gloriajaureguiapp.app.presentation.graph

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import pruebas.gloriajaureguiapp.mvi.Base

/**
 * Contiene los estados, eventos, efectos para la gráfica.
 */
class GraphModel {
    @Parcelize
    data class State(
        val isLoading: Boolean = false
    ) : Base.State, Parcelable

    sealed class Event : Base.Event {
        object OnGetData : Event()
    }

    sealed class SideEffect : Base.SideEffect {
        data class ShowData(val result: ArrayList<GraphResultset>) : SideEffect()
        data class ErrorShowData(val message: String) : SideEffect()
    }

    sealed class Result : Base.Result {
        sealed class Success : Result() {
            object OnResult : Success()
        }

        sealed class Failed : Result() {
            object OnResult : Failed()
        }
    }
}
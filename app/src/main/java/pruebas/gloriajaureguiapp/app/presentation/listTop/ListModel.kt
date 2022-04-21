package pruebas.gloriajaureguiapp.app.presentation.listTop

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pruebas.gloriajaureguiapp.mvi.Base

/**
 * Contiene los estados, eventos, efectos para la lista.
 */
class ListModel {
    @Parcelize
    data class State(
        val isLoading: Boolean = false
    ) : Base.State, Parcelable

    sealed class Event : Base.Event {
        object OnGetList : Event()
    }

    sealed class SideEffect : Base.SideEffect {
        data class ShowList(val result: ArrayList<ItemList>) : SideEffect()
        data class ErrorShowList(val message: String) : SideEffect()
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
package pruebas.gloriajaureguiapp.mvi

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import pruebas.gloriajaureguiapp.mvi.Base.Companion.SAVED_STATE

/**
 * Permite proveer un estado default al crear un ViewModel que herede de [BaseViewModel].
 *
 * Como realmente se trata de una ViewModel Factory, esta debe mandarse dentro de una lambda
 * en el 'lazy delegate' [Fragment.viewModels].
 *
 * @param defaultState el estado default que debe manejar un ViewModel que herede de [BaseViewModel].
 * @return [SavedStateViewModelFactory] una Factory de un ViewModel que reciba un [SavedStateHandle].
 */
fun Fragment.provideDefaultState(defaultState: Parcelable): SavedStateViewModelFactory {
    return SavedStateViewModelFactory(
        requireActivity().application,
        this,
        Bundle().apply { putParcelable(SAVED_STATE, defaultState) })
}

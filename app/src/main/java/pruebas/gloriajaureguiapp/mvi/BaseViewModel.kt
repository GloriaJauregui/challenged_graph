package pruebas.gloriajaureguiapp.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pruebas.gloriajaureguiapp.mvi.Base.Companion.SAVED_STATE

/**
 * Base para generar ViewModels que sigan un patrón de tipo MVI.
 *
 * [BaseViewModel] es una clase abstracta que debe ser heredada y tiene tres funciones abstractas
 * que deben ser implementadas.
 *
 * Espera se le definan tipos para las interfaces (State, Event, SideEffect, Result), las
 * cuales sólo tienen el objetivo de servir como 'tags' para los diferentes componentes que
 * conforman el modelo de MVI.
 *
 * @param S el estado manejado por el ViewModel. Usualmente una 'data class' de Kotlin.
 * @param E 'sealed class' que define todos los posibles eventos que pueden ocurrir para una vista.
 * @param SE 'sealed class' que define todos los posibles efectos secundarios para una vista.
 * @param R 'sealed class' de posibles resultados al procesar y reducir algún evento con el estado
 *          actual.
 * @property savedStateHandle mapa de llaves/valores para persistencia de parámetros cuando muere
 *                            el ViewModel. En este modelo, usualmente basta con guardar el estado,
 *                            puesto que ya contiene toda la información de la pantalla.
 */
abstract class BaseViewModel<S : Base.State, E : Base.Event, SE : Base.SideEffect, R : Base.Result>
    (private val savedStateHandle: SavedStateHandle) : ViewModel() {

    /**
     * El estado (state) actual de una vista. Este es el que se puede consultar cuando llega un
     * evento para hacer una reducción. Actualizar este valor acciona que se guarde en el
     * savedInstanceState para poder sobrevivir la muerte del ViewModel. La actualización también
     * provoca que el LiveData del estado se actualice, cambio que es observado por la vista para
     * actualizar sus parámetros.
     *
     * Como valor inicial se debe proveer una entidad del estado apropiado a través del
     * [androidx.fragment.app.Fragment.setArguments] de un Fragment, o del
     * [android.content.Intent.putExtra] de una Activity. Si no se provee un estado inicial se
     * lanza la excepción [NoDefaultStateException].
     */
    var state: S
        get() = savedStateHandle.get(SAVED_STATE) ?: throw Base.NoDefaultStateException()
        set(value) {
            savedStateHandle.set(SAVED_STATE, value)
            currentViewStateLiveData.value = value
        }

    /**
     * [MutableLiveData] del estado de la pantalla. No se expone.
     */
    private val currentViewStateLiveData = MutableLiveData<S>()

    /**
     * [MutableLiveData] de los efectos secundarios. No se expone.
     */
    private val currentViewEffectLiveData = MutableLiveData<SE>()

    /**
     * [LiveData] del estado de la pantalla. Debe observarse por un
     * [androidx.lifecycle.LifecycleOwner].
     */
    val viewState: LiveData<S>
        get() = currentViewStateLiveData

    /**
     * [LiveData] de los efectos secundarios. Debe observarse por un
     * [androidx.lifecycle.LifecycleOwner].
     */
    val viewEffect: LiveData<SE>
        get() = currentViewEffectLiveData

    /**
     * Se implementa para manejar todos los eventos que se vayan recibiendo en el ViewModel.
     * Como espera un tipo de [Event], que es una 'sealed class', se recomienda manejar la
     * implementación utilizando un 'when' exhaustivo.
     *
     * @param event el evento recibido en el ViewModel.
     */
    abstract fun onEvent(event: E)

    /**-
     * Se implementa para definir cómo deben ser las cargas de pantalla en una vista particular.
     *
     * @param isLoading indica si debe estarse cargando información.
     * @param loadingTextRes es el recurso con referencia al texto que se quiere mostrar en el
     *                       'Loading'.
     */
    abstract fun emitLoading(isLoading: Boolean = true, loadingTextRes: Int? = null)

    /**
     * Se implementa para manejar todos los resultados que se vayan generando al procesar los
     * eventos recibidos.
     * Como espera un tipo de [Result], que es una 'sealed class', se recomienda manejar la
     * implementación utilizando un 'when' exhaustivo.
     *
     * @param result el resultado calculado por el ViewModel al recibir un evento.
     */
    abstract fun onResult(result: R)

    /**
     * Auxilia para 'emitir' un efecto secundario al estar procesando un evento.
     *
     * @param sideEffect el efecto secundario que se quiere emitir.
     */
    fun emitSideEffect(sideEffect: SE) {
        currentViewEffectLiveData.value = sideEffect
    }

    /**
     * Auxilia para 'emitir' un nuevo estado de la vista.
     *
     * @param state el estado a emitir.
     */
    fun emitState(state: S) {
        this.state = state
    }

    /**
     * Auxilia para 'cargar' el estado que esté guardado en [savedStateHandle] una vez que se
     * destruyó el ViewModel. Usualmente debería usarse como alternativa cuando el
     * 'savedInstanceState' de un [androidx.lifecycle.LifecycleOwner] es nulo.
     */
    fun loadCurrentlySavedState() {
        emitState(state)
    }
}
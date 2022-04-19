package pruebas.gloriajaureguiapp.mvi

class Base {

    companion object {
        /**
         * Llave para guardar el estado de la vista en el savedStateHandle.
         */
        const val SAVED_STATE = "savedState"
    }

    /**
     * El estado (state) es un objeto que contiene todos los parámetros necesarios para construir
     * una pantalla.
     */
    interface State

    /**
     * Los eventos (event) son todas las cosas que pueden accionar un cambio en el estado de una
     * vista. Estos por lo general son causados por el usuario (seleccionar algo, borrar algo, etc),
     * pero también pueden ser causados por el sistema operativo (llega una push-notification por
     * ejemplo).
     */
    interface Event

    /**
     * Los efectos secundarios (side effects) son los resultados que no requieren alterar el estado
     * actual de una vista. Se podría considerar que son resultados de tipo 'one-shot', que sólo se
     * lanzan y no se espera hacer nada con ellos. Ejemplo de esto es mostrar un Snackbar, una
     * alerta de error, o alguna navegación.
     */
    interface SideEffect

    /**
     * Los resultados (results) son los cambios que se van a hacer al estado actual de una vista.
     * Cuando llega un evento nuevo, el ViewModel hace una reducción usando este evento y el estado
     * actual de la vista, para producir un nuevo estado. Los cambios al estado actual de la vista
     * que se necesiten para llegar al nuevo estado, son el resultado.
     */
    interface Result

    /**
     * Indica que no se encontró un estado predeterminado para la vista asociada al ViewModel actual.
     * Es importante porque este ViewModel no puede funcionar si no se define un estado inicial.
     */
    class NoDefaultStateException : Exception()
}
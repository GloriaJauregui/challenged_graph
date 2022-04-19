package pruebas.gloriajaureguiapp.mvi

/**
 * Wrapper para manejar las posibles respuestas de un servicio.
 * Un servicio puede ser exitoso (Successful) y contener una entidad como content;
 * o puede tener un error (Failed), y contener un mensaje de error.
 */
sealed class AppResponse<T> {
    data class Successful<T>(val content: T) : AppResponse<T>()
    data class Failed<T>(val errorMessage: String, val content: T? = null) : AppResponse<T>()
}
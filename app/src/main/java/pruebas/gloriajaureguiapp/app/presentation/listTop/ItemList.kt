package pruebas.gloriajaureguiapp.app.presentation.listTop

import pruebas.gloriajaureguiapp.apiservice.entities.ListTopResultset

/**
 * Interface para crear el arreglo de encabezado e items de la lista.
 */
sealed class ItemList {
    class Item(val data: ListTopResultset) : ItemList()
    class Header(val date: String) : ItemList()
}

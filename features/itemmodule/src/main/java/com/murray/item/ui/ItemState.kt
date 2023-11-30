package com.murray.item.ui

import com.murray.entities.items.Item

sealed class ItemState {
    data object NameEmptyError: ItemState() //RN-AC1: el nombre no deben ser valores nulos
    data object InvalidFormatRateError: ItemState() //RN-AC2: el precio debe ser un número
    data object TypeIsMandatoryError: ItemState() //RN-AC3: se debe seleccionar obligatoriamente un tipo
    data object NotEnoughDataError: ItemState() //RN-AC4: sólo se registrará el artículo si se introduce los datos necesarios

    //RN-AC5: si un artículo es tasable se le aplicará el IVA que se definirá en la configuración de la aplicación.
    data object ReferencedItem: ItemState() //RN-AC6: no se puede eliminar un artículo que ya esté en una factura.

    data class Success(var item: Item): ItemState()
}
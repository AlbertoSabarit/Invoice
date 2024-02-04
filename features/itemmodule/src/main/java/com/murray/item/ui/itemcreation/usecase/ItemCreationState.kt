package com.murray.item.ui.itemcreation.usecase

sealed class ItemCreationState {
    data object NameEmptyError: ItemCreationState() //RN-AC1: el nombre no deben ser valores nulos
    data object InvalidFormatRateError: ItemCreationState() //RN-AC2: el precio debe ser un número
    data object TypeIsMandatoryError: ItemCreationState() //RN-AC3: se debe seleccionar obligatoriamente un tipo
    //TODO data object NotEnoughDataError: ItemCreationState() //RN-AC4: sólo se registrará el artículo si se introduce los datos necesarios

    //TODO RN-AC5: si un artículo es tasable se le aplicará el IVA que se definirá en la configuración de la aplicación.
    //TODO data object ReferencedItem: ItemCreationState() //RN-AC6: no se puede eliminar un artículo que ya esté en una factura.
    data class ItemExistsError(var message:String): ItemCreationState()
    data object Success: ItemCreationState()
}
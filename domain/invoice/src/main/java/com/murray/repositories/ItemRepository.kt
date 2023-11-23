package com.murray.repositories

import com.murray.entities.items.Item
import com.murray.invoice.R


class ItemRepository private constructor() {
    companion object {
        val dataSet: MutableList<Item> = InitDataSetItem()

        private fun InitDataSetItem(): MutableList<Item> {
            var dataSet: MutableList<Item> = ArrayList()
            dataSet.add(Item(
                    "Maleta de Cuero",
                    "Producto",
                    "60€",
                    "Sí",
                    R.drawable.img_maleta_cuero
                )
            )
            dataSet.add(Item(
                    "Lápices Acuarela",
                    "Producto",
                    "75€",
                    "No",
                    R.drawable.img_lapices_acuarela
                )
            )
            dataSet.add(Item(
                    "Cuaderno",
                    "Producto",
                    "20€",
                    "No",
                    R.drawable.img_cuaderno
                )
            )
            dataSet.add(Item(
                    "Portátil",
                    "Producto",
                    "700€",
                    "Sí",
                    R.drawable.img_portatil
                )
            )
            dataSet.add(Item(
                    "Pinturas al óleo",
                    "Producto",
                    "9€",
                    "No",
                    R.drawable.img_oleo
                )
            )
            dataSet.add(Item(
                    "Botas de nieve",
                    "Producto",
                    "15€",
                    "Sí",
                    R.drawable.img_botas_nieve
                )
            )
            return dataSet
        }

    }
}
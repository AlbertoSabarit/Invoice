package com.murray.repositories

import com.murray.invoice.R

class ItemRepository private constructor(){
    companion object{
        val dataSet: MutableList<com.murray.entities.items.Item> = InitDataSetItem()

        private fun InitDataSetItem(): MutableList<com.murray.entities.items.Item> {
            var dataSet: MutableList<com.murray.entities.items.Item> = ArrayList()
            dataSet.add(
                com.murray.entities.items.Item(
                    "Maleta de Cuero",
                    "Producto",
                    "60€",
                    "Sí",
                    R.drawable.img_maleta_cuero
                )
            )
            dataSet.add(
                com.murray.entities.items.Item(
                    "Lápices Acuarela",
                    "Producto",
                    "75€",
                    "No",
                    R.drawable.img_lapices_acuarela
                )
            )
            dataSet.add(
                com.murray.entities.items.Item(
                    "Cuaderno",
                    "Producto",
                    "20€",
                    "No",
                    R.drawable.img_cuaderno
                )
            )
            dataSet.add(
                com.murray.entities.items.Item(
                    "Portátil",
                    "Producto",
                    "700€",
                    "Sí",
                    R.drawable.img_portatil
                )
            )
            dataSet.add(
                com.murray.entities.items.Item(
                    "Pinturas al óleo",
                    "Producto",
                    "9€",
                    "No",
                    R.drawable.img_oleo
                )
            )
            dataSet.add(
                com.murray.entities.items.Item(
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
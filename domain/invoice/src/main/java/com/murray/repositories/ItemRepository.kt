package com.murray.repositories

import android.net.Uri
import com.murray.data.items.Item
import com.murray.data.items.ItemId
import com.murray.data.items.ItemType
import com.murray.networkstate.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class ItemRepository private constructor() {

    companion object {
        private var dataSet: MutableList<Item> = mutableListOf()
        private var idIncrement: Int = 0;

        init {
            initDataSetItem()
        }

        fun addItem(
            name: String,
            type: ItemType,
            rate: Double,
            isTaxable: Boolean,
            description: String = "",
            imageUri: Uri? = null
        ) {
            val newItem = Item(
                name,
                type,
                rate,
                isTaxable,
                description,
                imageUri
            )
            dataSet.add(newItem)
        }
        fun getNextId():Int{
            return idIncrement++
        }
        fun getDataSetItem(): MutableList<Item> {
            return dataSet
        }

        suspend fun getItemList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(500)
                when {
                    dataSet.isEmpty() -> ResourceList.NoData(Exception("No hay artículos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Item>)
                }
            }
        }

        fun getItemById(id: Int) : Item{
            return dataSet.find { item -> item.id == id } as Item
        }

        private fun initDataSetItem(): MutableList<Item> {
            addItem(
                    "Maleta de Cuero",
                    ItemType.PRODUCT,
                    60.00,
                    true,
                    "Cuero de cabra de grano superior, cosido magistralmente con hilo encerado duradero para garantizar una bolsa de lona duradera. El forro interior de la bolsa es de lona resistente.  Tiene hebillas de latón de alta calidad. Hace que tu bolso se vea muy caro. Sólidos herrajes y construcción.",
            )
            addItem(
                "Lápices Acuarela",
                ItemType.PRODUCT,
                75.99,
                false,
                "Los ecolápices acuarelables Albrecht Dürer ofrecen a los artistas grandes posibilidades de expresión al trabajar con acuarelas. Materiales de alta calidad combinados con más de 250 años de experiencia han generado unos lápices acuarelables que producen inigualables efectos de acuarela de extraordinaria intensidad.",
            )
            addItem(
                "Cuaderno",
                ItemType.PRODUCT,
                20.00,
                false,
                "Cuaderno espiral el corte ingles frost basico folio tapa blanda 80h 60gr con margen colores surtidos. Producto certificado PEFC, de origen sostenible y ecológico. Papel de gran gramaje y blancura. Existen diferentes modelos. Se venden por separado. Se surtirán según existencias.",
            )
            addItem(
                "Portátil",
                ItemType.PRODUCT,
                699.95,
                true,
                "Sorprende al mundo con la potencia definitiva con este portátil. Está diseñado para ofrecer el máximo rendimiento para todo tipo de creatividad. Encontrar el equilibrio entre trabajar en los proyectos y terminar el contenido final es más fácil con un portátil rápido: pasarás menos tiempo esperando y más tiempo mejorando tu contenido. ",
            )
            addItem(
                "Pinturas al óleo",
                ItemType.PRODUCT,
                9.20,
                false,
                "La gama de colores al óleo extra-fino seguirá siendo inigualable por su pureza, calidad y fiabilidad - un éxito que se refleja en su reputación internacional entre los artistas profesionales. Esta gama se compone de 125 colores, ofreciendo el más amplio espectro de colores de pinturas al óleo de Winsor & Newton.",
            )
            addItem(
                "Botas de nieve",
                ItemType.PRODUCT,
                15.00,
                true,
                "¡Disfruta de los grandes espacios nevados! Estas botas altas impermeables de piel te abrigarán bien hasta -24 °C. Agarre óptimo en la nieve para disfrutar al máximo de tus salidas. ¿Quieres salir por caminos apisonados? Progresa con total comodidad gracias a la caña alta de piel y conserva los pies secos gracias a su impermeabilidad.",
            )
            return dataSet
        }

    }
}
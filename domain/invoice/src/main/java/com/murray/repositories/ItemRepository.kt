package com.murray.repositories

import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.invoice.R
import com.murray.network.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


enum class ImagesItem {
    MALETA_CUERO,
    LAPICES_ACUARELA,
    CUADERNO,
    PORTATIL,
    OLEO,
    BOTAS_NIEVE;

    companion object{
        fun getImageDrawable(itemImage: String):Int{
            return when(itemImage){
                MALETA_CUERO.name -> R.drawable.img_maleta_cuero
                LAPICES_ACUARELA.name -> R.drawable.img_lapices_acuarela
                CUADERNO.name -> R.drawable.img_cuaderno
                PORTATIL.name -> R.drawable.img_portatil
                OLEO.name -> R.drawable.img_oleo
                BOTAS_NIEVE.name -> R.drawable.img_botas_nieve
                else -> R.drawable.img_maleta_cuero //placeholder
            }
        }
    }
}

class ItemRepository private constructor() {

    companion object {
        private var dataSet: MutableList<Item> = InitDataSetItem()

        fun addItem(name:String, type:ItemType, rate: Double, isTaxable:Boolean, description: String, image:ImagesItem){
            dataSet.add(Item(name,type,rate,isTaxable,description,image))
        }

        fun getDataSetItem():MutableList<Item>{
            return dataSet
        }

        suspend fun getItemList():ResourceList{
            return withContext(Dispatchers.IO){
                delay(500)
                when{
                    dataSet.isEmpty() -> ResourceList.Error(Exception("No hay artículos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Item>)
                }
            }
        }

        private fun InitDataSetItem(): MutableList<Item> {
            var dataSet: MutableList<Item> = ArrayList()
            dataSet.add(
                Item(
                    "Maleta de Cuero",
                    ItemType.PRODUCT,
                    60.00,
                    true,
                    "Cuero de cabra de grano superior, cosido magistralmente con hilo encerado duradero para garantizar una bolsa de lona duradera. El forro interior de la bolsa es de lona resistente.  Tiene hebillas de latón de alta calidad. Hace que tu bolso se vea muy caro. Sólidos herrajes y construcción.",
                    ImagesItem.MALETA_CUERO,
                )
            )
            dataSet.add(
                Item(
                    "Lápices Acuarela",
                    ItemType.PRODUCT,
                    75.99,
                    false,
                    "Los ecolápices acuarelables Albrecht Dürer ofrecen a los artistas grandes posibilidades de expresión al trabajar con acuarelas. Materiales de alta calidad combinados con más de 250 años de experiencia han generado unos lápices acuarelables que producen inigualables efectos de acuarela de extraordinaria intensidad.",
                    ImagesItem.LAPICES_ACUARELA,
                )
            )
            dataSet.add(
                Item(
                    "Cuaderno",
                    ItemType.PRODUCT,
                    20.00,
                    false,
                    "Cuaderno espiral el corte ingles frost basico folio tapa blanda 80h 60gr con margen colores surtidos. Producto certificado PEFC, de origen sostenible y ecológico. Papel de gran gramaje y blancura. Existen diferentes modelos. Se venden por separado. Se surtirán según existencias.",
                    ImagesItem.CUADERNO
                )
            )
            dataSet.add(
                Item(
                    "Portátil",
                    ItemType.PRODUCT,
                    699.95,
                    true,
                    "Sorprende al mundo con la potencia definitiva con este portátil. Está diseñado para ofrecer el máximo rendimiento para todo tipo de creatividad. Encontrar el equilibrio entre trabajar en los proyectos y terminar el contenido final es más fácil con un portátil rápido: pasarás menos tiempo esperando y más tiempo mejorando tu contenido. ",
                    ImagesItem.PORTATIL
                )
            )
            dataSet.add(
                Item(
                    "Pinturas al óleo",
                    ItemType.PRODUCT,
                    9.20,
                    false,
                    "La gama de colores al óleo extra-fino seguirá siendo inigualable por su pureza, calidad y fiabilidad - un éxito que se refleja en su reputación internacional entre los artistas profesionales. Esta gama se compone de 125 colores, ofreciendo el más amplio espectro de colores de pinturas al óleo de Winsor & Newton.",
                    ImagesItem.OLEO
                )
            )
            dataSet.add(
                Item(
                    "Botas de nieve",
                    ItemType.PRODUCT,
                    15.00,
                    true,
                    "¡Disfruta de los grandes espacios nevados! Estas botas altas impermeables de piel te abrigarán bien hasta -24 °C. Agarre óptimo en la nieve para disfrutar al máximo de tus salidas. ¿Quieres salir por caminos apisonados? Progresa con total comodidad gracias a la caña alta de piel y conserva los pies secos gracias a su impermeabilidad.",
                    ImagesItem.BOTAS_NIEVE
                )
            )
            return dataSet
        }

    }
}
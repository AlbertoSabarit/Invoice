package com.murray.repositories

import com.murray.entities.items.Item
import com.murray.invoice.R


class ItemRepository private constructor() {
    companion object {
        val dataSet: MutableList<Item> = InitDataSetItem()

        private fun InitDataSetItem(): MutableList<Item> {
            var dataSet: MutableList<Item> = ArrayList()
            dataSet.add(
                Item(
                    "Maleta de Cuero",
                    "Producto",
                    "60€",
                    true,
                    "Cuero de cabra de grano superior superior, cosido magistralmente con hilo encerado duradero para garantizar una bolsa de lona duradera. El forro interior de la bolsa es de lona resistente.  Tiene hebillas de latón de alta calidad. Hace que tu bolso se vea muy caro. Sólidos herrajes y construcción.",
                    R.drawable.img_maleta_cuero,
                )
            )
            dataSet.add(
                Item(
                    "Lápices Acuarela",
                    "Producto",
                    "75€",
                    false,
                    "Los ecolápices acuarelables Albrecht Dürer ofrecen a los artistas grandes posibilidades de expresión al trabajar con acuarelas. Materiales de alta calidad combinados con más de 250 años de experiencia han generado unos lápices acuarelables que producen inigualables efectos de acuarela de extraordinaria intensidad.",
                    R.drawable.img_lapices_acuarela,

                    )
            )
            dataSet.add(
                Item(
                    "Cuaderno",
                    "Producto",
                    "20€",
                    false,
                    "Cuaderno espiral el corte ingles frost basico folio tapa blanda 80h 60gr con margen colores surtidos. Producto certificado PEFC, de origen sostenible y ecológico. Papel de gran gramaje y blancura. Existen diferentes modelos. Se venden por separado. Se surtirán según existencias.",
                    R.drawable.img_cuaderno
                )
            )
            dataSet.add(
                Item(
                    "Portátil",
                    "Producto",
                    "700€",
                    true,
                    "Sorprende al mundo con la potencia definitiva con este portátil. Está diseñado para ofrecer el máximo rendimiento para todo tipo de creatividad. Encontrar el equilibrio entre trabajar en los proyectos y terminar el contenido final es más fácil con un portátil rápido: pasarás menos tiempo esperando y más tiempo mejorando tu contenido. ",
                    R.drawable.img_portatil
                )
            )
            dataSet.add(
                Item(
                    "Pinturas al óleo",
                    "Producto",
                    "9€",
                    false,
                    "La gama de colores al óleo extra-fino seguirá siendo inigualable por su pureza, calidad y fiabilidad - un éxito que se refleja en su reputación internacional entre los artistas profesionales. Esta gama se compone de 125 colores, ofreciendo el más amplio espectro de colores de pinturas al óleo de Winsor & Newton.",
                    R.drawable.img_oleo
                )
            )
            dataSet.add(
                Item(
                    "Botas de nieve",
                    "Producto",
                    "15€",
                    true,
                    "¡Disfruta de los grandes espacios nevados! Estas botas altas impermeables de piel te abrigarán bien hasta -24 °C. Agarre óptimo en la nieve para disfrutar al máximo de tus salidas. ¿Quieres salir por caminos apisonados? Progresa con total comodidad gracias a la caña alta de piel y conserva los pies secos gracias a su impermeabilidad.",
                    R.drawable.img_botas_nieve
                )
            )
            return dataSet
        }

    }
}
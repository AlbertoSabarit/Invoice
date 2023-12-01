package com.murray.repositories

import com.murray.entities.customers.Customer

class CustomerRepository private constructor(){
    companion object {
        var dataSet: MutableList<Customer> = ArrayList()
        init{
            initDataSetUser()
        }

        fun getDataSetCustomer(): MutableList<Customer>{
            return dataSet
        }

        fun aaddCustomer(n:String, e:String, p:Int, c:String, a:String){
            dataSet.add(Customer(n, e, p, c ,a))
        }

        fun addCustomer(c:Customer){
            dataSet.add(c)
        }

        private fun initDataSetUser(){
            dataSet.add(Customer("Alejandro Valle", "alejandro@gmail.es", 693296746, "Torre del Mar", "Calle Juan Aguayo Moreno 3"))
            dataSet.add(Customer("Alberto Sabarit", "alberto@gmail.es", 620400868, "Rincón de la Victoria", "Calle José María Doblas 4"))
            dataSet.add(Customer("Ender Watts", "ender@gmail.uk",657318092,"Londres","9 Buckingham Gate"))
            dataSet.add(Customer("Katya Nikitenko", "katya@gmail.ua",643417845,"Kiev","Instytutska St 23-26"))
            dataSet.add(Customer("Lourdes Rodriguez", "Lourdes@gmail.com", 615940554, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Carlos Cortijo", "Carlos@gmail.com",639401967, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Federico Huercano", "Federico@gmail.com",682150437, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Francisco Garcia", "Francisco@gmail.com",668421541, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Eliseo Moreno", "Eliseo@gmail.com",682103410, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Francisco Cabrera", "Francisco@gmail.com",696412057, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Jose Millan", "Jose@gmail.com",604153895, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer("Pello Mir", "Pello@gmail.com",680259805, "Málaga", "8"))
        }
    }


}
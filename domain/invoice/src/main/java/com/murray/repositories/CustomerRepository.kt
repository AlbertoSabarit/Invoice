package com.murray.repositories

class CustomerRepository private constructor(){
    companion object {
        val dataSet: MutableList<com.murray.entities.customers.Customer> = initDataSetUser()
        private fun initDataSetUser(): MutableList<com.murray.entities.customers.Customer> {
            var dataset: MutableList<com.murray.entities.customers.Customer> = ArrayList()
            dataset.add(
                com.murray.entities.customers.Customer(
                    "Alejandro Valle",
                    "alejandro@gmail.es"
                )
            )
            dataset.add(com.murray.entities.customers.Customer("Alberto Sabarit", "alberto@gmail.es"))
            dataset.add(com.murray.entities.customers.Customer("Ender Watts", "ender@gmail.uk"))
            dataset.add(com.murray.entities.customers.Customer("Katya Nikitenko", "katya@gmail.ua"))
            dataset.add(
                com.murray.entities.customers.Customer(
                    "Lourdes Rodriguez",
                    "Lourdes@gmail.com"
                )
            )
            dataset.add(com.murray.entities.customers.Customer("Carlos Cortijo", "Carlos@gmail.com"))
            dataset.add(
                com.murray.entities.customers.Customer(
                    "Federico Huercano",
                    "Federico@gmail.com"
                )
            )
            dataset.add(
                com.murray.entities.customers.Customer(
                    "Francisco Garcia",
                    "Francisco@gmail.com"
                )
            )
            dataset.add(com.murray.entities.customers.Customer("Eliseo Moreno", "Eliseo@gmail.com"))
            dataset.add(
                com.murray.entities.customers.Customer(
                    "Francisco Cabrera",
                    "Francisco@gmail.com"
                )
            )
            dataset.add(com.murray.entities.customers.Customer("Jose Millan", "Jose@gmail.com"))
            return dataset
        }
    }


}
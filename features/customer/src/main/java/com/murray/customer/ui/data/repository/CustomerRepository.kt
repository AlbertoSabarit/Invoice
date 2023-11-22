package com.murray.customer.ui.data.repository

import com.murray.customer.ui.data.model.Customer

class CustomerRepository private constructor(){
    companion object {
        val dataSet: MutableList<Customer> = initDataSetUser()
        private fun initDataSetUser(): MutableList<Customer> {
            var dataset: MutableList<Customer> = ArrayList()
            dataset.add(Customer("Alejandro Valle", "alejandro@gmail.es"))
            dataset.add(Customer("Alberto Sabarit", "alberto@gmail.es"))
            dataset.add(Customer("Ender Watts", "ender@gmail.uk"))
            dataset.add(Customer("Katya Nikitenko", "katya@gmail.ua"))
            dataset.add(Customer("Lourdes Rodriguez", "Lourdes@gmail.com"))
            dataset.add(Customer("Carlos Cortijo", "Carlos@gmail.com"))
            dataset.add(Customer("Federico Huercano", "Federico@gmail.com"))
            dataset.add(Customer("Francisco Garcia", "Francisco@gmail.com"))
            dataset.add(Customer("Eliseo Moreno", "Eliseo@gmail.com"))
            dataset.add(Customer("Francisco Cabrera", "Francisco@gmail.com"))
            dataset.add(Customer("Jose Millan", "Jose@gmail.com"))
            return dataset
        }
    }


}
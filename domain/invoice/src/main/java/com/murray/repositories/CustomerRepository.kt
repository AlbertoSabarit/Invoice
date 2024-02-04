package com.murray.repositories

import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.networkstate.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CustomerRepository private constructor(){
    companion object {
        var dataSet: MutableList<Customer> = ArrayList()
        private var id: Int = 0
        fun getNextId():Int{
            return id++
        }
        init{
            initDataSetUser()
        }
        fun getCustomers():MutableList<Customer>{
            return dataSet
        }

        suspend fun getDataSetCustomer(): ResourceList{
            return withContext(Dispatchers.IO){
                when {
                    dataSet.isEmpty() -> ResourceList.NoData(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Customer>)
                }
            }
        }

        fun addCustomer(c:Customer){
            dataSet.add(c)
        }

        fun updateCustomer(_id: Int, n:String, e:String, p:Int?, c:String?, a:String?){
            dataSet[_id].name = n
            dataSet[_id].email.value = e
            dataSet[_id].phone = p
            dataSet[_id].city = c
            dataSet[_id].address = a
        }

        private fun initDataSetUser(){
        /*
            dataSet.add(Customer(getNextId(),"Alejandro Valle", Email("alejandro@gmail.es"), 693296746, "Torre del Mar", "Calle Juan Aguayo Moreno 3"))
            dataSet.add(Customer(getNextId(),"Alberto Sabarit", Email("alberto@gmail.es"), 620400868, "Rincón de la Victoria", "Calle José María Doblas 4"))
            dataSet.add(Customer(getNextId(),"Ender Watts", Email("ender@gmail.uk"),657318092,"Londres","9 Buckingham Gate"))
            dataSet.add(Customer(getNextId(),"Katya Nikitenko", Email("katya@gmail.ua"),643417845,"Kiev","Instytutska St 23-26"))
            dataSet.add(Customer(getNextId(),"Lourdes Rodriguez", Email("Lourdes@gmail.com"), 615940554, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Carlos Cortijo", Email("Carlos@gmail.com"),639401967, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Federico Huercano", Email("Federico@gmail.com"),682150437, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Francisco Garcia", Email("Francisco@gmail.com"),668421541, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Eliseo Moreno", Email("Eliseo@gmail.com"),682103410, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Francisco Cabrera", Email("Francisco@gmail.com"),696412057, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Jose Millan", Email("Jose@gmail.com"),604153895, "Málaga", "Calle Competa 29"))
            dataSet.add(Customer(getNextId(),"Pello Mir", Email("Pello@gmail.com"),680259805, "Málaga", "8"))
            */
        }
    }


}
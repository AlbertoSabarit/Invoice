package com.murray.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.murray.data.accounts.Account
import com.murray.data.accounts.BusinessProfile
import com.murray.data.accounts.Email
import com.murray.data.accounts.User
import com.murray.data.converter.AccountIdTypeConverter
import com.murray.data.converter.CustomerTypeConverter
import com.murray.data.converter.EmailTypeConverter
import com.murray.data.converter.InvoiceTypeConverter
import com.murray.data.converter.ItemTypeConverter
import com.murray.data.converter.UriTypeConverter
import com.murray.data.customers.Customer
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import com.murray.data.tasks.Task
import com.murray.database.dao.AccountDao
import com.murray.database.dao.BusinessProfileDao
import com.murray.database.dao.CustomerDao
import com.murray.database.dao.InvoiceDao
import com.murray.database.dao.ItemDao
import com.murray.database.dao.LineItemsDao
import com.murray.database.dao.TaskDao
import com.murray.database.dao.UserDao
import com.murray.invoice.Locator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


@Database(
    entities = [Account::class, BusinessProfile::class, User::class, Customer::class, Task::class, Item::class, Invoice::class, LineItems::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    AccountIdTypeConverter::class,
    EmailTypeConverter::class,
    CustomerTypeConverter::class,
    UriTypeConverter::class,
    //  InvoiceTypeConverter::class,
    ItemTypeConverter::class
)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun businessProfileDao(): BusinessProfileDao
    abstract fun customerDao(): CustomerDao
    abstract fun taskDao(): TaskDao
    abstract fun itemDao(): ItemDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun lineitemsDao(): LineItemsDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        fun getInstance(): InvoiceDatabase {
            return INSTANCE ?: synchronized(InvoiceDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(): InvoiceDatabase {
            return Room.databaseBuilder(
                Locator.requireApplication,
                InvoiceDatabase::class.java,
                "Facturas"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addTypeConverter(AccountIdTypeConverter())
                .addTypeConverter(EmailTypeConverter())
                .addTypeConverter(CustomerTypeConverter())
                .addTypeConverter(UriTypeConverter())
                //.addTypeConverter(InvoiceTypeConverter())
                .addTypeConverter(ItemTypeConverter())
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: InvoiceDatabase?) : RoomDatabase.Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }

        private fun populateDatabase() {
            populateUsers()
            populateCustomers()
            populateItems()
            //populateTasks()
        }

        private fun populateUsers() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.userDao().insert(
                    User(
                        "Alberto",
                        "Sabarit",
                        Email("blbertosabarit@iesportada.org")
                    )
                )
                invoiceDatabase.userDao().insert(
                    User(
                        "Ender",
                        "Watts",
                        Email("enderwatts@iesportada.org")
                    )
                )
                invoiceDatabase.userDao().insert(
                    User(
                        "Kateryna",
                        "Nikitenko",
                        Email("katerynanikitenko@iesportada.org")
                    )
                )
                invoiceDatabase.userDao().insert(
                    User(
                        "Alejandro",
                        "Valle",
                        Email("alevalle@iesportada.org")
                    )
                )
                invoiceDatabase.userDao().insert(
                    User(
                        "Javier",
                        "Zarcia",
                        Email("kavier@iesportada.org")
                    )
                )
            }
        }

        private fun populateCustomers() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.customerDao().insert(
                    Customer(
                        "Alberto Sabarit",
                        Email("sabarit@iesportada.org"),
                        620400868,
                        "Rincón de la Victoria",
                        "Urb las pedrizas"
                    )
                )
                invoiceDatabase.customerDao().insert(
                    Customer(
                        "Alejandro Valle",
                        Email("alejandro@gmail.es"),
                        693296746,
                        "Torre del Mar",
                        "Calle Juan Aguayo Moreno 3"
                    )
                )
                invoiceDatabase.customerDao().insert(
                    Customer(
                        "Ender Watts",
                        Email("watts@gmail.uk"),
                        657318092,
                        "Londres",
                        "9 Buckingham Gate"
                    )
                )
                invoiceDatabase.customerDao().insert(
                    Customer(
                        "Katya Nikitenko",
                        Email("NikitenkoKatya@gmail.ua"),
                        643417845,
                        "Chernigov",
                        "Kam'yans'ka Sloboda"
                    )
                )
                invoiceDatabase.customerDao().insert(
                    Customer(
                        "Lourdes Rodriguez",
                        Email("Lourdes@gmail.com"),
                        615940554,
                        "Málaga",
                        "Calle Competa 29"
                    )
                )
            }
        }
        private fun populateItems() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Balón de Oro",
                        ItemType.Producto,
                        60.0,
                        true,
                        "El mejor balon",
                        null
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Camiseta",
                        ItemType.Servicio,
                        50.0,
                        false,
                        "Camiseta del Real Madrid",
                        null
                    )
                )
            }
        }
        private fun populateTasks() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.taskDao().insert(
                    Task(
                        "Entrevista",
                        Customer(
                            "Alberto",
                            Email("blbertosabarit@iesportada.org"),
                            620400868,
                            "Malaga",
                            "Urb las pedrizas"
                        ),
                        "Privado",
                        "27/02/2024",
                        "27/02/2024",
                        "Pendiente",
                        "Entrevista con el futuro balon de oro belligoal"
                    )
                )
            }
        }
    }
}
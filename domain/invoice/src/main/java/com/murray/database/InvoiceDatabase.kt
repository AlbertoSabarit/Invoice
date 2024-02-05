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
import com.murray.data.converter.UriTypeConverter
import com.murray.data.customers.Customer
import com.murray.data.items.Item
import com.murray.data.tasks.Task
import com.murray.database.dao.AccountDao
import com.murray.database.dao.BusinessProfileDao
import com.murray.database.dao.CustomerDao
import com.murray.database.dao.ItemDao
import com.murray.database.dao.TaskDao
import com.murray.database.dao.UserDao
import com.murray.invoice.Locator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


@Database(
    entities = [Account::class, BusinessProfile::class, User::class, Customer::class, Task::class, Item::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    AccountIdTypeConverter::class,
    EmailTypeConverter::class,
    CustomerTypeConverter::class,
    UriTypeConverter::class
)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun businessProfileDao(): BusinessProfileDao
    abstract fun customerDao(): CustomerDao
    abstract fun taskDao(): TaskDao
    abstract fun itemDao(): ItemDao

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

        private suspend fun populateDatabase() {
            //populateUsers()
        }

        private suspend fun populateUsers() {
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
                        "Carlos",
                        "Valle",
                        Email("zlevalle@iesportada.org")
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
    }
}
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
import com.murray.data.converter.EmailTypeConverter
import com.murray.data.customers.Customer
import com.murray.database.dao.AccountDao
import com.murray.database.dao.BusinessProfileDao
import com.murray.database.dao.CustomerDao
import com.murray.database.dao.UserDao
import com.murray.invoice.Locator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Database(
    entities = [Account::class, BusinessProfile::class, User::class, Customer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AccountIdTypeConverter::class, EmailTypeConverter::class)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun businessProfile(): BusinessProfileDao
    abstract fun customerDao(): CustomerDao

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
                "Invoice"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .addTypeConverter(AccountIdTypeConverter())
                .addTypeConverter(EmailTypeConverter())
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: InvoiceDatabase?) : RoomDatabase.Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Esperar a que se complete la inserción de datos
            runBlocking {
                applicationScope.launch(Dispatchers.IO) {
                    populateDatabase()
                }.join()
            }
        }

        private suspend fun populateDatabase() {
            populateUsers()
        }

        private suspend fun populateUsers() {

            instance.let { invoiceDatabase ->
                invoiceDatabase?.userDao()?.insert(
                    User(
                        "Alberto",
                        "Sabarit",
                        Email("blbertosabarit@iesportada.org")
                    )
                )
                invoiceDatabase?.userDao()?.insert(
                    User(
                        "Ender",
                        "Watts",
                        Email("enderwatts@iesportada.org")
                    )
                )
                invoiceDatabase?.userDao()?.insert(
                    User(
                        "Kateryna",
                        "Nikitenko",
                        Email("katerynanikitenko@iesportada.org")
                    )
                )
                invoiceDatabase?.userDao()?.insert(
                    User(
                        "Carlos",
                        "Valle",
                        Email("zlevalle@iesportada.org")
                    )
                )
                invoiceDatabase?.userDao()?.insert(
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
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
import com.murray.data.converter.ItemTypeConverter
import com.murray.data.converter.TaskIdTypeConverter
import com.murray.data.converter.UriTypeConverter
import com.murray.data.customers.Customer
import com.murray.data.items.Item
import com.murray.data.items.ItemType
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
import kotlinx.coroutines.runBlocking


@Database(
    entities = [Account::class, BusinessProfile::class, User::class, Customer::class, Task::class, Item::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    AccountIdTypeConverter::class,
    EmailTypeConverter::class,
    CustomerTypeConverter::class,
    UriTypeConverter::class,
    ItemTypeConverter::class
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

        private suspend fun populateItems() {
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Maleta de Cuero",
                        ItemType.PRODUCT,
                        60.00,
                        true,
                        "Cuero de cabra de grano superior, cosido magistralmente con hilo encerado duradero para garantizar una bolsa de lona duradera. El forro interior de la bolsa es de lona resistente.  Tiene hebillas de latón de alta calidad. Hace que tu bolso se vea muy caro. Sólidos herrajes y construcción.",
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Lápices Acuarela",
                        ItemType.PRODUCT,
                        75.99,
                        false,
                        "Los ecolápices acuarelables Albrecht Dürer ofrecen a los artistas grandes posibilidades de expresión al trabajar con acuarelas. Materiales de alta calidad combinados con más de 250 años de experiencia han generado unos lápices acuarelables que producen inigualables efectos de acuarela de extraordinaria intensidad.",
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Cuaderno",
                        ItemType.PRODUCT,
                        20.00,
                        false,
                        "Cuaderno espiral el corte ingles frost basico folio tapa blanda 80h 60gr con margen colores surtidos. Producto certificado PEFC, de origen sostenible y ecológico. Papel de gran gramaje y blancura. Existen diferentes modelos. Se venden por separado. Se surtirán según existencias.",
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Portátil",
                        ItemType.PRODUCT,
                        699.95,
                        true,
                        "Sorprende al mundo con la potencia definitiva con este portátil. Está diseñado para ofrecer el máximo rendimiento para todo tipo de creatividad. Encontrar el equilibrio entre trabajar en los proyectos y terminar el contenido final es más fácil con un portátil rápido: pasarás menos tiempo esperando y más tiempo mejorando tu contenido. ",
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Pinturas al óleo",
                        ItemType.PRODUCT,
                        9.20,
                        false,
                        "La gama de colores al óleo extra-fino seguirá siendo inigualable por su pureza, calidad y fiabilidad - un éxito que se refleja en su reputación internacional entre los artistas profesionales. Esta gama se compone de 125 colores, ofreciendo el más amplio espectro de colores de pinturas al óleo de Winsor & Newton.",
                    )
                )
                invoiceDatabase.itemDao().insert(
                    Item(
                        "Botas de nieve",
                        ItemType.PRODUCT,
                        15.00,
                        true,
                        "¡Disfruta de los grandes espacios nevados! Estas botas altas impermeables de piel te abrigarán bien hasta -24 °C. Agarre óptimo en la nieve para disfrutar al máximo de tus salidas. ¿Quieres salir por caminos apisonados? Progresa con total comodidad gracias a la caña alta de piel y conserva los pies secos gracias a su impermeabilidad.",
                    )
                )
            }
        }
    }
}
package com.murray.data.items
import android.net.Uri
import android.os.Parcel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ItemTest {

    @Mock
    lateinit var mockUri: Uri

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(mockUri.toString()).thenReturn("https://example.com")
    }

    @Test
    fun crearItem_comprobacionItemType_True() {
        val item1 = Item("Item1", ItemType.Producto, 10.0, false, "")
        val item2 = Item("Item2", ItemType.Servicio, 15.0, true, "")

        assertTrue(item1.type == ItemType.Producto)
        assertTrue(item2.type == ItemType.Servicio)
    }

    @Test
    fun crearItem_comprobacionId_True() {
        val item1 = Item("Item1", ItemType.Producto, 10.0, false, "")
        val item2 = Item("Item2", ItemType.Servicio, 15.0, true, "")

        assertTrue(item1.id >= 0)
        assertTrue(item2.id >= 0)
    }

    @Test
    fun crearItem_comprobacionConstructorVacio_True() {
        val item = Item()

        assertEquals("", item.name)
        assertEquals(ItemType.Producto, item.type)
        assertEquals(0.0, item.rate, 0.0)
        assertEquals(false, item.isTaxable)
        assertEquals("", item.description)
        assertEquals(null, item.imageUri)
    }

    @Test
    fun compareTo_item1MayorItem2_True() {
        val item1 = Item("Item1", ItemType.Producto, 10.0, false, "")
        val item2 = Item("Item2", ItemType.Servicio, 15.0, true, "")

        assertEquals(-1, item1.compareTo(item2))
        assertEquals(1, item2.compareTo(item1))
    }

    @Test
    fun writeToParcel_createFromParcel_True() {
        val originalItem = Item("Test", ItemType.Producto, 20.0, true, "Descripcion", mockUri)
        val parcel = Parcel.obtain()
        originalItem.writeToParcel(parcel, 0)

        parcel.setDataPosition(0)

        val itemFromParcel = Item.CREATOR.createFromParcel(parcel)

        assertEquals(originalItem.name, itemFromParcel.name)
        assertEquals(originalItem.rate, itemFromParcel.rate)
        assertEquals(originalItem.isTaxable, itemFromParcel.isTaxable)
        assertEquals(originalItem.description, itemFromParcel.description)
    }
}

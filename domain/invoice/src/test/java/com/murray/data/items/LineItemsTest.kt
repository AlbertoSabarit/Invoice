package com.murray.data.items

import android.os.Parcel
import com.murray.data.invoices.LineItems
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LineItemsTest {
   /* @Test
    fun testParcelReadWrite() {
        val item = Item("Item 2", ItemType.Producto, 20.0, false, "Description")
        val lineItem = LineItems(1, item, 3, 20.0, "Description", 10)

        val parcel = Parcel.obtain()
        lineItem.writeToParcel(parcel, lineItem.describeContents())
        parcel.setDataPosition(0)

        val lineItemFromParcel = LineItems.CREATOR.createFromParcel(parcel)

        assertEquals(lineItem.invoiceId, lineItemFromParcel.invoiceId)
        assertEquals(lineItem.item, lineItemFromParcel.item)
        assertEquals(lineItem.cantidad, lineItemFromParcel.cantidad)
        assertEquals(lineItem.price, lineItemFromParcel.price, 0.0)
        assertEquals(lineItem.descripcion, lineItemFromParcel.descripcion)
        assertEquals(lineItem.iva, lineItemFromParcel.iva)
    }*/

    @Test
    fun testDefaultConstructor() {
        val lineItem = LineItems()

        assertEquals(0, lineItem.invoiceId)
        assertEquals(Item(), lineItem.item)
        assertEquals(0, lineItem.cantidad)
        assertEquals(0.0, lineItem.price, 0.0)
        assertEquals("", lineItem.descripcion)
        assertEquals(0, lineItem.iva)
    }
}
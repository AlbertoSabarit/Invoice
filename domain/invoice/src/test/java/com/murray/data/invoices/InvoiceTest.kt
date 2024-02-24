package com.murray.data.invoices

import android.net.Uri
import com.murray.data.customers.Customer
import org.junit.Test
import android.os.Parcel
import com.murray.data.accounts.Email
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class InvoiceTest {
    private val invoice = Invoice.create(Customer(), "12/02/2021", "15/03/2021", arrayListOf())
    /*@Test
    fun createInvoice_DefaultConstructor() {
        val invoice = Invoice()
        assertEquals(Customer(), invoice.cliente)
        assertEquals("", invoice.fcreacion)
        assertEquals("", invoice.fvencimiento)
        assertEquals(ArrayList<LineItems>(), invoice.lineItems)
    }*/

    @Test
    fun createInvoice_CustomConstructor() {
        val cliente = Customer("John Doe", Email("john@example.com"), 1234, "City", "Address")
        val item = Item("Item 1", ItemType.Producto, 10.0, true, "Description")
        val lineItems = arrayListOf(LineItems(1, item, 2, 10.0, "Description", 5))
        val invoice = Invoice.create(cliente, "2024-02-24", "2024-03-24", lineItems)

        assertEquals("2024-02-24", invoice.fcreacion)
        assertEquals("2024-03-24", invoice.fvencimiento)
        assertEquals(cliente, invoice.cliente)
        assertEquals(1, invoice.lineItems.size)
        assertEquals(1, invoice.lineItems[0].invoiceId)
        assertEquals(item, invoice.lineItems[0].item)
        assertEquals(2, invoice.lineItems[0].cantidad)
        assertEquals(10.0, invoice.lineItems[0].price, 0.0)
        assertEquals("Description", invoice.lineItems[0].descripcion)
        assertEquals(5, invoice.lineItems[0].iva)
    }

    @Test
    fun testInvoiceComparison() {
        val invoice1 = Invoice.createDefaultInvoice()
        val invoice2 = Invoice.createDefaultInvoice()
        invoice1.fcreacion = "2024-02-24"
        invoice2.fcreacion = "2024-02-25"
        assertEquals(-1, invoice1.compareTo(invoice2))
        assertEquals(1, invoice2.compareTo(invoice1))
        assertEquals(0, invoice1.compareTo(invoice1))
    }

   /* @Test
    fun testParcelReadWrite() {
        val parcel = mock<Parcel>()

        val cliente = Customer("Jane Doe", Email("jane@example.com"), 9876, "City", "Address")
        val item = Item("Item 2", ItemType.Producto, 20.0, false, "Description")
        val lineItems = arrayListOf(LineItems(2, item, 3, 20.0, "Description", 10))
        val originalInvoice = Invoice.create(cliente, "2024-02-24", "2024-03-24", lineItems)

        doNothing().`when`(parcel).writeParcelable(originalInvoice, 0)

        doReturn(originalInvoice).`when`(parcel).readParcelable<Invoice>(Invoice::class.java.classLoader)

        originalInvoice.writeToParcel(parcel, 0)

        parcel.setDataPosition(0)

        val invoiceFromParcel = Invoice.CREATOR.createFromParcel(parcel)

        assertEquals(originalInvoice, invoiceFromParcel)
    }*/


}
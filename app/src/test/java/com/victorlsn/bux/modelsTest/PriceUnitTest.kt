package com.victorlsn.bux.modelsTest

import com.victorlsn.bux.data.api.models.MarketStatus
import com.victorlsn.bux.data.api.models.Price
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class PriceUnitTest {

    private val price = Price()

    @Test
    fun testGetFormattedPriceNullity() {
        assertEquals(null, price.getFormattedPrice())

        price.amount = "229.75"

        assertEquals(null, price.getFormattedPrice())

        price.currency = "USD"

        assertEquals(null, price.getFormattedPrice())

        price.decimals = 2

        assertNotEquals(null, price.getFormattedPrice())
    }

    @Test
    fun testGetFormattedCurrency() {
        price.amount = "229.75"
        price.currency = "EUR"
        price.decimals = 2

        assertEquals("229,75 €", price.getFormattedPrice())

        price.currency = "USD"

        assertEquals("$229.75", price.getFormattedPrice())
    }
}

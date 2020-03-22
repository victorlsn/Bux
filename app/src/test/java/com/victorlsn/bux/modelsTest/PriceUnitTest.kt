package com.victorlsn.bux.modelsTest

import com.victorlsn.bux.data.api.models.Price
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PriceUnitTest {

    private val price = Price()

    @Test
    fun testGetFormattedPriceNullity() {
        assertEquals(price.getFormattedPrice(), null)

        price.amount = "229.75"

        assertEquals(price.getFormattedPrice(), null)

        price.currency = "USD"

        assertEquals(price.getFormattedPrice(), null)

        price.decimals = 5

        assertNotEquals(price.getFormattedPrice(), null)
    }

    @Test
    fun testGetFormattedCurrency() {
        price.amount = "229.75"
        price.currency = "EUR"
        price.decimals = 2

        assertEquals(price.getFormattedPrice(), "EUR 229,75")

        price.currency = "USD"

        assertEquals(price.getFormattedPrice(), "US$ 229,75")
    }
}

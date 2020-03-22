package com.victorlsn.bux.modelsTest

import com.victorlsn.bux.data.api.models.MarketStatus
import com.victorlsn.bux.data.api.models.Price
import com.victorlsn.bux.data.api.models.Product
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductUnitTest {

    private val product = Product()

    @Before
    fun init() {
        product.marketStatus = MarketStatus.OPEN
        product.symbol = "EUR/USD"
        product.displayName = "EUR/USD"
        product.securityId = "sb242460"
        product.currentPrice = Price()
        product.closingPrice = Price()
    }

    @Test
    fun testGetFormattedPriceDiff() {
        assertEquals(null, product.getFormattedPriceDiff())

        product.currentPrice.amount = "1500"

        assertEquals(null, product.getFormattedPriceDiff())

        product.closingPrice.amount = "1000"

        assertEquals("+ 50%", product.getFormattedPriceDiff())

        product.currentPrice.amount = "500"

        assertEquals("- 50%", product.getFormattedPriceDiff())
    }
}
package com.victorlsn.bux.modelsTest

import com.victorlsn.bux.data.api.models.Price
import com.victorlsn.bux.data.api.models.Range
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class RangeUnitTest {

    private val range = Range()

    @Before
    fun init() {
        Locale.setDefault(Locale("en", "US"))
    }

    @Test
    fun testGetFormattedRangeNullity() {
        assertEquals(range.getFormattedRange(), null)

        range.low = "229.75"

        assertEquals(range.getFormattedRange(), null)

        range.currency = "USD"

        assertEquals(range.getFormattedRange(), null)

        range.decimals = 2

        assertEquals(range.getFormattedRange(), null)

        range.high = "245.30"

        assertNotEquals(range.getFormattedRange(), null)
    }

    @Test
    fun testGetFormattedRange() {
        range.low = "229.75"
        range.high = "245.30"
        range.currency = "EUR"
        range.decimals = 2

        assertEquals("229,75 € - 245,30 €", range.getFormattedRange())

        range.currency = "USD"

        assertEquals("$229.75 - $245.30", range.getFormattedRange())
    }
}

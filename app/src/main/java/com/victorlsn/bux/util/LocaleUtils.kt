package com.victorlsn.bux.util

import java.text.NumberFormat
import java.util.*

fun getLocalFromISO(iso4217code: String): Locale {
    for (locale in NumberFormat.getAvailableLocales()) {
        val code: String =
            NumberFormat.getCurrencyInstance(locale).currency.currencyCode
        if (iso4217code == code) {
            return locale
        }
    }
    return Locale.getDefault()
}
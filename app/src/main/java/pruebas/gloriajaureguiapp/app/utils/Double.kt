package pruebas.gloriajaureguiapp.app.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

fun Double.formatAsCurrency(locale: Locale = Locale.US, numberOfDecimals: Int = 2): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    return currencyFormatter.format(this.roundHalfUp(numberOfDecimals))
}

fun Double.roundHalfUp(digits: Int = 2): BigDecimal {
    return (BigDecimal(this)).setScale(digits, RoundingMode.HALF_UP)
}
package br.ufpe.cin.walletshare.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Double.percent(value: Double): Double {
    val percent = this * value
    return this + percent
}

fun Double.currencyFormatting(): String {
    val numberFormat = NumberFormat.getCurrencyInstance()
    return numberFormat.format(this)
}

fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("dd/MM/yyy hh:mm")
    return format.format(this)
}

fun String.currencyInputFormatting(): String {
    return this.currencyFormattingToDouble().currencyFormatting()
}

fun String.currencyFormattingToDouble(): Double {
    val regex = Regex("[^0-9]")
    val numbers =  regex.replace(this, "")
    return if (numbers.isEmpty()) 0.0 else (numbers.toDouble() / 100)
}
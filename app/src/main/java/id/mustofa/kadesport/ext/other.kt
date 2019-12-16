/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import java.util.*

fun Int?.str() = if (this == null) "-" else "$this"

fun String?.str() = this ?: "-"

fun String?.splitComma() = this?.split(';')?.dropLastWhile { it.isBlank() }

fun String?.splitLiner() = if (isNullOrBlank()) "-" else splitComma()?.joinToString("\n")

fun isBlank(vararg values: String?) = values.all { it.isNullOrBlank() }

fun currentTimeMillis() = Calendar.getInstance(Locale.getDefault()).timeInMillis

/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

fun Int?.str() = if (this == null) "-" else "$this"

fun String?.str() = this ?: "-"

fun String?.split() = this?.split(';')?.dropLastWhile { it.isBlank() }

fun String?.splitLiner() = split()?.joinToString("\n")
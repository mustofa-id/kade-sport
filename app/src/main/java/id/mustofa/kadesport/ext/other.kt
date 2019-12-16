/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import id.mustofa.kadesport.R
import java.util.*

private val socialIcons = mapOf(
  "Website" to R.drawable.ic_website,
  "Facebook" to R.drawable.ic_facebook,
  "Twitter" to R.drawable.ic_twitter,
  "Instagram" to R.drawable.ic_instagram,
  "YouTube" to R.drawable.ic_youtube
)

fun Int?.dashable() = if (this == null) "-" else "$this"

fun String?.dashable() = this ?: "-"

fun String?.splitComma() = this?.split(';')?.dropLastWhile { it.isBlank() }

fun String?.splitLiner() = if (isNullOrBlank()) "-" else splitComma()?.joinToString("\n")

fun String.toIconLink() = socialIcons[this] ?: R.drawable.ic_link

fun isBlank(vararg values: String?) = values.all { it.isNullOrBlank() }

fun currentTimeMillis() = Calendar.getInstance(Locale.getDefault()).timeInMillis

/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import androidx.annotation.DrawableRes

data class League(
  val id: Long,
  val name: String,
  val description: String,
  @DrawableRes val badgePath: Int
)
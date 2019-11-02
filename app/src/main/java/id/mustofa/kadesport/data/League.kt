/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class League(

  @SerializedName("idLeague")
  val id: Long,

  @SerializedName("strLeague")
  val name: String,

  @SerializedName("strDescriptionEN")
  val description: String,

  @DrawableRes val badgePath: Int,

  @SerializedName("strBadge")
  val badgeUrl: String = ""
)
/*
 * Mustofa on 11/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import com.google.gson.annotations.SerializedName

data class Team(

  @SerializedName("idTeam")
  val id: Long,

  @SerializedName("strTeam")
  val name: String,

  @SerializedName("strTeamBadge")
  val badgePath: String
)
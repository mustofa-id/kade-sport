/*
 * Mustofa on 11/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.entity

import com.google.gson.annotations.SerializedName
import id.mustofa.kadesport.data.entity.base.Entity

data class Team(

  @SerializedName("idTeam")
  override val id: Long,

  @SerializedName("strTeam")
  val name: String,

  @SerializedName("strTeamBadge")
  val badgePath: String
) : Entity
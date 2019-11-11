/*
 * Mustofa on 11/3/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import com.google.gson.annotations.SerializedName

data class LeagueEventMin(

  @SerializedName("idEvent")
  val id: Long,

  @SerializedName("strHomeTeam")
  val home: String = "-",

  @SerializedName("strAwayTeam")
  val away: String = "-",

  @SerializedName("intHomeScore")
  val homeScore: Int?,

  @SerializedName("intAwayScore")
  val awayScore: Int?,

  @SerializedName("idHomeTeam")
  val idHome: Long,

  @SerializedName("idAwayTeam")
  val idAway: Long,

  @SerializedName("strDate")
  val date: String?,

  @SerializedName("strTime")
  val time: String?,

  var homeBadgePath: String?,
  var awayBadgePath: String?,

  @SerializedName("strSport")
  val sport: String?
)
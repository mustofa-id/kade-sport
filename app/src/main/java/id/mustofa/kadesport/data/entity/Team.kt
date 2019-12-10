/*
 * Mustofa on 11/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.entity

import com.google.gson.annotations.SerializedName
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.util.PrimaryKey

data class Team(

  @PrimaryKey
  @SerializedName("idTeam")
  override val id: Long,

  @SerializedName("intLoved")
  val loved: Int,

  @SerializedName("strTeam")
  val name: String,

  @SerializedName("intFormedYear")
  val formedYear: Int,

  @SerializedName("strSport")
  val sport: String,

  @SerializedName("strLeague")
  val league: String,

  @SerializedName("idLeague")
  val leagueId: Long,

  @SerializedName("strDivision")
  val division: String,

  @SerializedName("strManager")
  val manager: String,

  @SerializedName("strStadium")
  val stadium: String,

  @SerializedName("strKeywords")
  val keywords: String,

  @SerializedName("strStadiumThumb")
  val stadiumThumb: String,

  @SerializedName("strStadiumDescription")
  val stadiumDescription: String,

  @SerializedName("strStadiumLocation")
  val stadiumLocation: String,

  @SerializedName("intStadiumCapacity")
  val stadiumCapacity: Int,

  @SerializedName("strWebsite")
  val website: String,

  @SerializedName("strFacebook")
  val facebook: String,

  @SerializedName("strTwitter")
  val twitter: String,

  @SerializedName("strInstagram")
  val instagram: String,

  @SerializedName("strDescriptionEN")
  val description: String,

  @SerializedName("strGender")
  val gender: String,

  @SerializedName("strCountry")
  val country: String,

  @SerializedName("strTeamBadge")
  val badgePath: String,

  @SerializedName("strTeamJersey")
  val jersey: String,

  @SerializedName("strTeamLogo")
  val logo: String,

  @SerializedName("strTeamFanart1")
  val fanart: String,

  @SerializedName("strTeamFanart2")
  val fanart2: String,

  @SerializedName("strTeamFanart3")
  val fanart3: String,

  @SerializedName("strTeamFanart4")
  val fanart4: String,

  @SerializedName("strTeamBanner")
  val banner: String,

  @SerializedName("strYoutube")
  val youtube: String
) : Entity
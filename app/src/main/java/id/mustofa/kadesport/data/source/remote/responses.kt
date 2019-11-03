/*
 * Mustofa on 11/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.remote

import com.google.gson.annotations.SerializedName
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent

data class LeagueResponse(
  val leagues: List<League>?
)

data class LeagueEventResponse(

  // Search will using alternate value although the result is json array.
  @SerializedName(value = "events", alternate = ["event"])
  val events: List<LeagueEvent>
)
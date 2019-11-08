/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.LeagueEventMin
import id.mustofa.kadesport.data.State

interface LeagueRepository {

  suspend fun fetchAllLeagues(): State<List<League>>

  suspend fun fetchLeagueById(id: Long): State<League>

  suspend fun fetchEventsNextLeague(
    leagueId: Long,
    badge: Boolean = false
  ): State<List<LeagueEventMin>>

  suspend fun fetchEventsPastLeague(
    leagueId: Long,
    badge: Boolean = false
  ): State<List<LeagueEventMin>>

  suspend fun fetchEventById(id: Long, fetchTeam: Boolean = false): State<LeagueEvent>

  suspend fun searchEvents(query: String): State<List<LeagueEventMin>>
}

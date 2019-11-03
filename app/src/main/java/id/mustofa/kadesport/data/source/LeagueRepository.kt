/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State

interface LeagueRepository {

  suspend fun fetchAllLeagues(): State<List<League>>

  suspend fun fetchLeagueById(id: Long): State<League>

  suspend fun fetchEventsNextLeague(eventId: Long): State<List<LeagueEvent>>

  suspend fun fetchEventsPastLeague(eventId: Long): State<List<LeagueEvent>>

  suspend fun fetchEventById(id: Long): State<LeagueEvent>

  suspend fun searchEvents(query: String): State<List<LeagueEvent>>
}

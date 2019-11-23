/*
 * Mustofa on 11/16/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import com.google.gson.Gson
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.LeagueEventResponse

class FakeTheSportDb {

  private val gson by lazy { Gson() }

  fun lookupLeague(id: Long): League {
    return LeagueDataSource()
      .getLeagues()
      .first { it.id == id }
  }

  fun eventsNextLeague() = jsonOf<LeagueEventResponse>("eventsnextleague.json")?.events!!

  fun eventsPastLeague() = jsonOf<LeagueEventResponse>("eventspastleague.json")?.events!!

  fun eventById(eventId: Long): LeagueEvent {
    val events = eventsNextLeague() + eventsPastLeague()
    return events.first { it.id == eventId }
  }

  fun favoriteEvents(): List<LeagueEvent> {
    // filter team name contain `man` like Man United or Man City
    return searchEvents("man")
  }

  fun searchEvents(query: String): List<LeagueEvent> {
    return (eventsNextLeague() + eventsPastLeague()).filter {
      "${it.homeName} ${it.awayName}".contains(query, true)
    }
  }

  private inline fun <reified T> jsonOf(resName: String) = javaClass.classLoader
    ?.getResourceAsStream(resName)
    ?.reader()
    ?.let { gson.fromJson(it, T::class.java) }
}
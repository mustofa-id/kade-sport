/*
 * Mustofa on 11/16/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import com.google.gson.Gson
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.LeagueEventResponse

class FakeTheSportDb {

  private val gson by lazy { Gson() }

  fun lookupLeague(id: Long): League {
    return LeagueDataSource()
      .getLeagues()
      .first { it.id == id }
  }

  fun eventsNextLeague() = readJson("eventsnextleague.json")

  fun eventsPastLeague() = readJson("eventspastleague.json")

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

  private fun readJson(fileName: String) = javaClass.classLoader
    ?.getResourceAsStream(fileName)
    ?.reader()
    ?.let { gson.fromJson(it, LeagueEventResponse::class.java) }
    ?.events!!
}
/*
 * Mustofa on 11/16/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.google.gson.Gson
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.LeagueEventResponse

@VisibleForTesting(otherwise = PRIVATE)
class FakeTheSportDb {

  private val gson by lazy { Gson() }

  fun lookupLeague(id: Long): League {
    return LeagueDataSource()
      .getLeagues()
      .first { it.id == id }
  }

  fun eventsNextLeague() = jsonOf<LeagueEventResponse>("eventsnextleague.json")?.events!!

  fun eventsPastLeague() = jsonOf<LeagueEventResponse>("eventspastleague.json")?.events!!

  fun eventById(eventId: Long): Event {
    val events = eventsNextLeague() + eventsPastLeague()
    return events.first { it.id == eventId }
  }

  fun favoriteEvents(): List<Event> {
    // filter team name contain `man` like Man United or Man City
    return searchEvents("man")
  }

  fun searchEvents(query: String): List<Event> {
    return (eventsNextLeague() + eventsPastLeague()).filter {
      "${it.homeName} ${it.awayName}".contains(query, true)
    }
  }

  private inline fun <reified T> jsonOf(resName: String) = javaClass.classLoader
    ?.getResourceAsStream(resName)
    ?.reader()
    ?.let { gson.fromJson(it, T::class.java) }
}
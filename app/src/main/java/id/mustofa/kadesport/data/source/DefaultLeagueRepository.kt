/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import android.util.Log
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.delay

class DefaultLeagueRepository(
  private val leagueDataSource: LeagueDataSource,
  private val theSportDbService: TheSportDbService
) : LeagueRepository {

  override suspend fun fetchAllLeagues(): State<List<League>> {
    delay(500) // fake network request
    return Success(leagueDataSource.getLeagues())
  }

  override suspend fun fetchLeagueById(id: Long) = handleError {
    val response = theSportDbService.lookupLeague(id)
    response.body()?.leagues?.get(0)
  }

  override suspend fun fetchEventsNextLeague(eventId: Long) = handleError {
    val response = theSportDbService.eventsNextLeague(eventId)
    response.body()?.events
  }

  override suspend fun fetchEventsPastLeague(eventId: Long) = handleError {
    val response = theSportDbService.eventsPastLeague(eventId)
    response.body()?.events
  }

  override suspend fun fetchEventById(id: Long) = handleError {
    val response = theSportDbService.lookupEvent(id)
    response.body()?.events?.get(0)
  }

  override suspend fun searchEvents(query: String) = handleError {
    val response = theSportDbService.searchEvents(query)
    response.body()?.events
  }

  private inline fun <T> handleError(data: () -> T?): State<T> {
    return try {
      val result = data() ?: return Error(R.string.msg_empty_result)
      Success(result)
    } catch (e: Exception) {
      Log.e(javaClass.name, "handleError: ", e)
      Error(R.string.msg_failed_result)
    }
  }
}
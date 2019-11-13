/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import android.util.Log
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.Team
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.local.EventDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.delay

class DefaultLeagueRepository(
  private val leagueDataSource: LeagueDataSource,
  private val cacheableSportService: TheSportDbService,
  private val sportService: TheSportDbService,
  private val eventDataSource: EventDataSource
) : LeagueRepository {

  override suspend fun fetchAllLeagues(): State<List<League>> {
    delay(500) // fake network request
    return Success(leagueDataSource.getLeagues())
  }

  override suspend fun fetchLeagueById(id: Long) = handleError {
    val response = cacheableSportService.lookupLeague(id)
    response.body()?.leagues?.get(0)
  }

  override suspend fun fetchEventsNextLeague(leagueId: Long, badge: Boolean) = handleError {
    val response = sportService.eventsNextLeague(leagueId)
    response.body()?.events?.applyBadge(badge)
  }

  override suspend fun fetchEventsPastLeague(leagueId: Long, badge: Boolean) = handleError {
    val response = sportService.eventsPastLeague(leagueId)
    response.body()?.events?.applyBadge(badge)
  }

  override suspend fun fetchEventById(id: Long, badge: Boolean) = handleError {
    val response = sportService.lookupEvent(id)
    response.body()?.events?.applyBadge(badge)?.get(0)
  }

  override suspend fun searchEvents(query: String) = handleError {
    val response = sportService.searchEvents(query)
    response.body()?.events
      ?.filter { it.sport == "Soccer" }
      ?.applyBadge(true)
  }

  override suspend fun getAllFavoriteEvents() = handleError {
    eventDataSource.getAllFavorites()
  }

  override suspend fun getFavoriteEventById(eventId: Long) = handleError {
    eventDataSource.getFavorite(eventId)
  }

  override suspend fun addEventToFavorite(event: LeagueEvent): State<Boolean> {
    return handleError(R.string.msg_failed_add_fav) {
      eventDataSource.addFavorite(event) != 0L
    }
  }

  override suspend fun removeEventFromFavorite(eventId: Long): State<Boolean> {
    return handleError(R.string.msg_failed_remove_fav) {
      eventDataSource.removeFavorite(eventId) != 0
    }
  }

  // -- private logic --
  private suspend fun fetchTeamById(id: Long): Team? {
    val response = cacheableSportService.lookupTeam(id)
    return response.body()?.teams?.get(0)
  }

  private suspend fun List<LeagueEvent>.applyBadge(constraint: Boolean) =
    if (!constraint) this
    else map {
      it.apply {
        homeBadgePath = fetchTeamById(it.homeTeamId)?.badgePath
        awayBadgePath = fetchTeamById(it.awayTeamId)?.badgePath
      }
    }

  private inline fun <T> handleError(
    error: Int = R.string.msg_failed_result,
    data: () -> T?
  ): State<T> {
    return try {
      val result = data() ?: return Error(R.string.msg_empty_result)
      if (result is List<*> && result.isEmpty()) return Error(R.string.msg_empty_result)
      if (result is Boolean && !result) return Error(error)
      Success(result)
    } catch (e: Exception) {
      Log.e(javaClass.name, "handleError: ", e)
      Error(error)
    }
  }
}
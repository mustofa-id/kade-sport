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
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.delay
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultLeagueRepository(
  private val leagueDataSource: LeagueDataSource,
  private val theSportDbService: TheSportDbService
) : LeagueRepository {

  // TODO: Doesn't work
  private var cachedTeams: ConcurrentMap<Long, Team>? = null

  override suspend fun fetchAllLeagues(): State<List<League>> {
    delay(500) // fake network request
    return Success(leagueDataSource.getLeagues())
  }

  override suspend fun fetchLeagueById(id: Long) = handleError {
    val response = theSportDbService.lookupLeague(id)
    response.body()?.leagues?.get(0)
  }

  override suspend fun fetchEventsNextLeague(leagueId: Long, badge: Boolean) = handleError {
    val response = theSportDbService.eventsNextLeague(leagueId)
    response.body()?.events?.applyBadge(badge)
  }

  override suspend fun fetchEventsPastLeague(leagueId: Long, badge: Boolean) = handleError {
    val response = theSportDbService.eventsPastLeague(leagueId)
    response.body()?.events?.applyBadge(badge)
  }

  override suspend fun fetchEventById(id: Long) = handleError {
    val response = theSportDbService.lookupEvent(id)
    response.body()?.events?.get(0)
  }

  override suspend fun searchEvents(query: String) = handleError {
    val response = theSportDbService.searchEvents(query)
    response.body()?.events
  }

  private suspend fun fetchTeamById(id: Long): Team? {
    Log.d(javaClass.name, "fetchTeamById:: CACHE: ${cachedTeams?.size}")
    val response = theSportDbService.lookupTeam(id)
    return cachedTeams?.get(id) ?: response.body()?.teams?.get(0)?.also {
      if (cachedTeams == null) {
        cachedTeams = ConcurrentHashMap()
      }
      cachedTeams?.put(id, it)
    }
  }

  private suspend fun List<LeagueEvent>.applyBadge(constraint: Boolean): List<LeagueEvent> {
    if (!constraint) return this
    return map {
      it.apply {
        homeBadgePath = fetchTeamById(it.idHome)?.badgePath
        awayBadgePath = fetchTeamById(it.idAway)?.badgePath
      }
    }
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
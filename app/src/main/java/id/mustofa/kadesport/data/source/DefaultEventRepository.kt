/*
 * Mustofa on 11/29/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.source.local.EventDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import id.mustofa.kadesport.data.source.remote.handleResponse
import id.mustofa.kadesport.data.source.remote.successOf
import id.mustofa.kadesport.data.source.repository.EventRepository
import id.mustofa.kadesport.data.source.repository.EventRepository.EventType

class DefaultEventRepository(
  private val remoteSource: TheSportDbService,
  private val cacheableRemoteSource: TheSportDbService,
  private val localSource: EventDataSource
) : EventRepository {

  override suspend fun get(id: Long): State<Event> {
    val response = handleResponse { remoteSource.lookupEvent(id) }
    return successOf(response) {
      val data = events?.get(0) ?: return Empty
      data.applyBadge()
      Success(data)
    }
  }

  override suspend fun getAll(leagueId: Long, type: EventType, badge: Boolean): State<List<Event>> {
    val response = handleResponse {
      when (type) {
        EventType.NEXT -> remoteSource.eventsNextLeague(leagueId)
        EventType.PAST -> remoteSource.eventsPastLeague(leagueId)
      }
    }

    return successOf(response) {
      val data = events ?: return Empty
      if (badge) data.forEach { it.applyBadge() }
      Success(data)
    }
  }

  override suspend fun search(query: String): State<List<Event>> {
    val response = handleResponse { remoteSource.searchEvents(query) }
    return successOf(response) {
      val data = events
        ?.filter { it.sport == "Soccer" }
        ?.apply { forEach { it.applyBadge() } }
      if (data.isNullOrEmpty()) return Empty
      Success(data)
    }
  }

  override suspend fun getFavorite(eventId: Long): State<Event> {
    val favorite = localSource.getFavorite(eventId) ?: return Empty
    return Success(favorite)
  }

  override suspend fun getFavorites(): State<List<Event>> {
    val favorites = localSource.getAllFavorites()
    if (favorites.isEmpty()) return Empty
    return Success(favorites)
  }

  override suspend fun addFavorite(event: Event): State<Boolean> {
    val add = localSource.addFavorite(event)
    return if (add > 0) Success(true) else Error(R.string.msg_failed_add_fav)
  }

  override suspend fun removeFavorite(id: Long): State<Boolean> {
    val remove = localSource.removeFavorite(id)
    return if (remove > 0) Success(true) else Error(R.string.msg_failed_remove_fav)
  }

  private suspend fun Event.applyBadge() {
    suspend fun team(id: Long) = cacheableRemoteSource
      .lookupTeam(id).body()?.teams?.get(0)
    homeBadgePath = team(homeTeamId)?.badgePath
    awayBadgePath = team(awayTeamId)?.badgePath
  }
}
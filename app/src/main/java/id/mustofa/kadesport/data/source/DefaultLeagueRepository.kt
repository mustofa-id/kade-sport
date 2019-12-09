/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import id.mustofa.kadesport.data.source.remote.handleResponse
import id.mustofa.kadesport.data.source.remote.successOf
import id.mustofa.kadesport.data.source.repository.LeagueRepository
import id.mustofa.kadesport.util.wrapWithIdlingResource
import kotlinx.coroutines.delay

class DefaultLeagueRepository(
  private val embeddedSource: LeagueDataSource,
  private val cacheableRemoteSource: TheSportDbService
) : LeagueRepository {

  override suspend fun getAll(): State<List<League>> {
    return wrapWithIdlingResource {
      delay(500) // fake network request
      Success(embeddedSource.getLeagues())
    }
  }

  override suspend fun get(id: Long): State<League> {
    val response = handleResponse { cacheableRemoteSource.lookupLeague(id) }
    return successOf(response) {
      val data = leagues?.get(0) ?: return State.Empty
      Success(data)
    }
  }
}
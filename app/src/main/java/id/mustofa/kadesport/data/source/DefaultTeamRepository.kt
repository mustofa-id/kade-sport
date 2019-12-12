/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Empty
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.data.source.local.TeamDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import id.mustofa.kadesport.data.source.remote.handleResponse
import id.mustofa.kadesport.data.source.remote.successOf
import id.mustofa.kadesport.data.source.repository.TeamRepository

class DefaultTeamRepository(
  private val cacheableRemoteSource: TheSportDbService,
  private val localSource: TeamDataSource
) : TeamRepository {

  override suspend fun get(id: Long): State<Team> {
    val response = handleResponse { cacheableRemoteSource.lookupTeam(id) }
    return successOf(response) {
      val data = teams
        ?.firstOrNull { it.id == id }
        ?: return Empty
      Success(data)
    }
  }

  override suspend fun getAll(leagueId: Long): State<List<Team>> {
    val response = handleResponse { cacheableRemoteSource.lookupAllTeam(leagueId) }
    return successOf(response) {
      val data = this.teams ?: return Empty
      Success(data)
    }
  }

  override suspend fun search(query: String): State<List<Team>> {
    val response = handleResponse { cacheableRemoteSource.searchTeams(query) }
    return successOf(response) {
      val data = this.teams?.filter {
        it.sport == "Soccer"
      } ?: return Empty
      Success(data)
    }
  }

  override suspend fun getFavorite(teamId: Long): State<Team> {
    val favorite = localSource.getFavorite(teamId) ?: return Empty
    return Success(favorite)
  }

  override suspend fun getFavorites(): State<List<Team>> {
    val favorites = localSource.getFavorites()
    if (favorites.isEmpty()) return Empty
    return Success(favorites)
  }

  override suspend fun addFavorite(team: Team): State<Int> {
    val add = localSource.saveFavorite(team)
    return if (add > 0) Success(R.string.msg_ok_add_fav)
    else State.Error(R.string.msg_failed_add_fav)
  }

  override suspend fun removeFavorite(teamId: Long): State<Int> {
    val remove = localSource.deleteFavorite(teamId)
    return if (remove > 0) Success(R.string.msg_ok_remove_fav)
    else State.Error(R.string.msg_failed_remove_fav)
  }
}
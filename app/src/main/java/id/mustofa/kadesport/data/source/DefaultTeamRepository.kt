/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

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
    TODO("not implemented")
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
    TODO("not implemented")
  }

  override suspend fun getFavorites(): State<List<Team>> {
    TODO("not implemented")
  }

  override suspend fun addFavorite(team: Team): State<Boolean> {
    TODO("not implemented")
  }

  override suspend fun removeFavorite(teamId: Long): State<Boolean> {
    TODO("not implemented")
  }
}
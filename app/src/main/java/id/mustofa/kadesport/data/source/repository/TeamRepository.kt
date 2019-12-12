/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.repository

import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Team

interface TeamRepository {

  suspend fun get(id: Long): State<Team>

  suspend fun getAll(leagueId: Long): State<List<Team>>

  suspend fun search(query: String): State<List<Team>>

  suspend fun getFavorite(teamId: Long): State<Team>

  suspend fun getFavorites(): State<List<Team>>

  suspend fun addFavorite(team: Team): State<Int>

  suspend fun removeFavorite(teamId: Long): State<Int>
}
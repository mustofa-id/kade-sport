/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.repository

import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State

interface LeagueRepository {

  suspend fun get(id: Long): State<League>

  suspend fun getAll(): State<List<League>>
}

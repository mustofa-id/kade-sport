/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.repository

import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.League

interface LeagueRepository {

  suspend fun get(id: Long): State<League>

  suspend fun getAll(): State<List<League>>
}

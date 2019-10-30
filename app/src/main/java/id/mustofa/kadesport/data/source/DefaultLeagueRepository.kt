/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource

class DefaultLeagueRepository : LeagueRepository {

  override fun fetchAllLeagues(): List<League> {
    return LeagueDataSource.getLeagues()
  }
}
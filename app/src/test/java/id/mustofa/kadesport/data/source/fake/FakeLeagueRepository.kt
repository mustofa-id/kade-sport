/*
 * Mustofa on 1/2/20
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.fake

import id.mustofa.kadesport.data.FakeTheSportDb
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.entity.Standing
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.repository.LeagueRepository

class FakeLeagueRepository : LeagueRepository {

  private val leagueDataSource = LeagueDataSource()
  private val fakeDataSource = FakeTheSportDb()

  override suspend fun get(id: Long): State<League> {
    val league = fakeDataSource.lookupLeague(id)
    return Success(league)
  }

  override suspend fun getAll(): State<List<League>> {
    return Success(leagueDataSource.getLeagues())
  }

  override suspend fun getStandings(id: Long): State<List<Standing>> {
    val standingsTable = fakeDataSource.lookupStandingsTable()
    return Success(standingsTable)
  }
}
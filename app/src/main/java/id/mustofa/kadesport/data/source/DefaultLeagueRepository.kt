/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source

import android.util.Log
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.Error
import id.mustofa.kadesport.data.State.Success
import id.mustofa.kadesport.data.source.embedded.LeagueDataSource
import id.mustofa.kadesport.data.source.remote.TheSportDbService
import kotlinx.coroutines.delay

class DefaultLeagueRepository(
  private val leagueDataSource: LeagueDataSource,
  private val theSportDbService: TheSportDbService
) : LeagueRepository {

  override suspend fun fetchAllLeagues(): State<List<League>> {
    delay(800) // fake network request
    return Success(leagueDataSource.getLeagues())
  }

  override suspend fun fetchLeagueById(id: Long): State<League> {
    return try {
      val response = theSportDbService.lookupLeague(id)
      val league = response.body()?.leagues?.get(0) ?: return Error(R.string.msg_empty_result)
      Success(league)
    } catch (e: Exception) {
      Log.e(javaClass.name, "fetchLeagueById: ", e)
      Error(R.string.msg_failed_result)
    }
  }
}